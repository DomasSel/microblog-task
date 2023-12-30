INSERT INTO USERS VALUES
(1,'admin@gmail.com','$2a$10$gqHrslMttQWSsDSVRTK1OehkkBiXsJ/a4z2OURU./dizwOQu5Lovu','admin'),
(2,'test@gmail.com','$2a$10$gqHrslMttQWSsDSVRTK1OehkkBiXsJ/a4z2OURU./dizwOQu5Lovu','test');
INSERT INTO ROLES VALUES (1,'ROLE_ADMIN'),(2,'ROLE_USER');
INSERT INTO USERS_ROLES VALUES (1,1),(2,2);