Deploy:
```
mvn clean package spring-boot:repackage
curl -L -o rieserver_bk.db https://api.rie.recipes/rieserver.db
eb deploy
```

# rie-server
http://rieserver-java17.eba-phfh2nm2.us-east-2.elasticbeanstalk.com
