# Rest nosql

#### Simple REST API that makes use of a mongo nosql database that is run in a docker container.

**Using our docker image:**

Run the docker file with the following command:

`docker run --name my-mongo -d -v /tmp/mongodb:/data/db -p 27017:27017 mongo`

The following command should show the docker image is running:

`docker ps`

This command will open a bash shell inside the image:

`docker exec -it my-mongo bash`

This command will open the mongo shell when inside the image:

`mongo`

**Using our mongo shell:**

Create a database: `use DATABASE_NAME`

Create a collection: `db.post.insert({"postId":1, "userId":1, "title":"Jack", "body":"I grew up in bristol"})`

Query a collection: `db.post.find().pretty()`
