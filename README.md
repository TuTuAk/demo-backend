# Getting Started
> Moi Kiitos is a application like weibo, with only users and microposts.
> It provides some features as following shows, due to time constraints, 
> these features are not perfect.

# Features
- user register
- user login
- send a new post
- search user by username or email address 
- get all posts
- view followings, followers
- follow/unfollow a user


# How to run ?
> You should install all the following dependencies to prepare the running environment.
> Then import the SQL script into mysql which have provided in "resources/db/moikkitos.sql"
> The script contains all the related table schemas and mock data.
> Then you can run DemoBackendApplication to start the backend service,
> For frontend service you just need to run 'yarn install' -> 'yarn serve'

- backend
    - Mysql 8.0
    - Redis 7.0.11
    - Jdk11

- frontend
    - npm
    - vue3.0
    - axios
    - element-ui
