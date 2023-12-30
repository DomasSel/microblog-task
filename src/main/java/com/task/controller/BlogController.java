package com.task.controller;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.task.dto.BlogDto;
import com.task.entity.Blog;
import com.task.entity.Role;
import com.task.entity.User;
import com.task.repo.BlogRepository;
import com.task.repo.UserRepository;
import com.task.service.WordOperationsService;

@RestController
@RequestMapping("/api")
public class BlogController {
	
	@Autowired
	BlogRepository blogRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	WordOperationsService wordOperationService;

	@PostMapping(value = "/blogs/create-blog")
	public ResponseEntity<BlogDto> createBlog(@RequestBody Blog blog) {
		
		Authentication _authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = _authentication.getName();
        User user = userRepository.findByUsername(currentPrincipalName);
        
        if(user == null) {
        	throw new UsernameNotFoundException("User does not exist by Username");
        }
		try {
			Blog _blog = blogRepository
		          .save(new Blog(blog.getTitle(), blog.getBody(), user));
			
			BlogDto _blogDto = new BlogDto();
			_blogDto.setTitle(_blog.getTitle());
			_blogDto.setBody(_blog.getBody());
			_blogDto.setAuthorName(_blog.getAuthorName());

			return new ResponseEntity<>(_blogDto, HttpStatus.CREATED);
	    } catch (Exception e) {
	      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	@PostMapping(value = "/blogs/{id}/delete-blog")
	public ResponseEntity<String> deleteBlog(@PathVariable("id") long id) {
		
		Authentication _authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = _authentication.getName();
        User user = userRepository.findByUsername(currentPrincipalName);
        
        if(user == null) {
        	throw new UsernameNotFoundException("User does not exist by Username");
        }
		try {
			Blog _blog = blogRepository.getById(id);
			
			if(_blog.getAuthor() != user) {
				return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
			}
			else {
				blogRepository.delete(_blog);
				return new ResponseEntity<>("Blog with id:"+_blog.getId()+" successfully deleted.", HttpStatus.OK);
			}
	    } catch (Exception e) {
	      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	@PostMapping(value = "/blogs/{id}/edit-blog")
	public ResponseEntity<String> editBlog(@PathVariable("id") long id, @RequestBody Blog blog) {
		
		Authentication _authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = _authentication.getName();
        User user = userRepository.findByUsername(currentPrincipalName);
        
        if(user == null) {
        	throw new UsernameNotFoundException("User does not exist by Username");
        }
		try {
			Blog _blog = blogRepository.getById(id);
			
			if(_blog.getAuthor() != user) {
				return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
			}
			else {
				_blog.setTitle(blog.getTitle());
				_blog.setBody(blog.getBody());
				blogRepository.save(_blog);
				
				return new ResponseEntity<>("Blog with id:"+_blog.getId()+" successfully edited.", HttpStatus.OK);
			}
	    } catch (Exception e) {
	      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	@GetMapping(value = "/blogs/{id}")
	public ResponseEntity<BlogDto> viewBlog(@PathVariable("id") long id) {
		try {
			
			Blog _blog = blogRepository.findById(id).get();
			_blog.setViews(_blog.getViews() + 1);
			blogRepository.save(_blog);
			
			BlogDto _blogDto = new BlogDto();
			_blogDto.setTitle(_blog.getTitle());
			_blogDto.setBody(_blog.getBody());
			_blogDto.setAuthorName(_blog.getAuthorName());
			_blogDto.setViews(_blog.getViews());
			
			return new ResponseEntity<>(_blogDto, HttpStatus.OK);
	    } catch (Exception e) {
	      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	@GetMapping(value = "/blogs/{id}/word-count")
	public ResponseEntity<Integer> blogWordCountSingle(@PathVariable("id") long id) {
		try {
	        int wordCount = wordOperationService.getWordCount(blogRepository.getById(id).getBody());
	        return new ResponseEntity<>(wordCount, HttpStatus.OK);
	    } catch (Exception e) {
	    	return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	@GetMapping(value = "/blogs/word-count")
	public ResponseEntity<Integer> blogWordCountAll() {
		try {
			AtomicInteger wordCount = new AtomicInteger(0);
			blogRepository.findAll().forEach(
					blog -> wordCount.addAndGet(wordOperationService.getWordCount(blog.getBody())));
	        return new ResponseEntity<>(wordCount.get(), HttpStatus.OK);
	    } catch (Exception e) {
	    	return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	@GetMapping(value = "/blogs/{id}/word-recurrence")
	public ResponseEntity<?> blogWordRecurrenceSingle(@PathVariable("id") long id) {
		try {
			Map<String, Integer> wordRecurrenceMap = 
					wordOperationService.sortMapByDescendingOrder(
							wordOperationService.getWordRecurrenceMap(blogRepository.getById(id).getBody())
					);
	        return new ResponseEntity<>(wordRecurrenceMap, HttpStatus.OK);
	    } catch (Exception e) {
	    	return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	@GetMapping(value = "/blogs/word-recurrence")
	public ResponseEntity<Map<String, Integer>> blogWordRecurrenceAll() {
		try {
			Map<String, Integer> mergedWordRecurrenceMap = new TreeMap<>();
			
			blogRepository.findAll().forEach(blog -> 
				mergedWordRecurrenceMap.putAll(
					wordOperationService.mergeMaps(
							mergedWordRecurrenceMap, 
							wordOperationService.getWordRecurrenceMap(blog.getBody())
					)
				)
			);
	        return new ResponseEntity<>(wordOperationService.sortMapByDescendingOrder(
	        		mergedWordRecurrenceMap), HttpStatus.OK);
	    } catch (Exception e) {
	    	return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
}
