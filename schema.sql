CREATE TABLE users(
   username       VARCHAR(20) PRIMARY KEY     NOT NULL,
   created_at     INTEGER     NOT NULL
);

CREATE TABLE recipes(
   id             VARCHAR(30) PRIMARY KEY     NOT NULL,
   name           VARCHAR(100)                NOT NULL,
   slug           VARCHAR(30)                NOT NULL,
   description    VARCHAR(500)                NOT NULL,
   recipe         VARCHAR(3000)               NOT NULL,
   author         VARCHAR(12)                NOT NULL,
   created_at     INTEGER     NOT NULL,
   deleted_at     INTEGER     ,
   CONSTRAINT fk_recipes_author
      FOREIGN KEY(author) 
      REFERENCES users(username),
   CONSTRAINT unique_slug 
      UNIQUE (slug, deleted_at)
);

CREATE TABLE news(
   id             VARCHAR(12) PRIMARY KEY     NOT NULL,
   type           VARCHAR(100)                NOT NULL,
   text           VARCHAR(500)                NOT NULL,
   title          VARCHAR(100)                NOT NULL,
   author       VARCHAR(12)                NOT NULL,
   created_at      INTEGER    NOT NULL,
   recipe_slug       VARCHAR(12),
   CONSTRAINT fk_news_recipe_slug
      FOREIGN KEY(recipe_slug) 
      REFERENCES recipes(slug),
   CONSTRAINT fk_news_author
      FOREIGN KEY(author) 
      REFERENCES users(username)
);

INSERT into users (username, created_at) VALUES ('Sari', CURRENT_TIMESTAMP);