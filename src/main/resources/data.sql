INSERT INTO users (name) VALUES ('CPU');
INSERT INTO users (name) VALUES ('ほんだ');
INSERT INTO users (name) VALUES ('いがき');
INSERT INTO matchinfo (user1,user2,user1Hand,isActive) VALUES (1,2,'rock','FALSE');
INSERT INTO matchinfo (user1,user2,user1Hand,isActive) VALUES (2,3,'scissors','FALSE');
INSERT INTO matches (user1,user2,user1Hand,user2Hand,isActive) VALUES (2,1,'rock','scissors','FALSE');
INSERT INTO matches (user1,user2,user1Hand,user2Hand,isActive) VALUES (2,1,'rock','rock','FALSE');
INSERT INTO matches (user1,user2,user1Hand,user2Hand,isActive) VALUES (2,1,'rock','paper','FALSE');
