# Poll app project report

### Set up and installation
I built a full-stack Poll App with a Spring Boot backend and a React frontend.  
The backend stores users, polls, options, and votes in memory, so no database. 
The frontend is SPA communicates with the backend via REST endpoints.

Little technical stack:
- Spring Boot 3.5.5 with Java 21
- React + Vite for the frontend
- fetch() for communication
- CSS for styling (make components pretty💅)
- Gradle for building and running the backend

## Issues encountered 🙃

### 1. bootRun suddenly disappeared
At first, I could run `./gradlew bootRun` just fine and then cannot🙀 (no idea what I did)
After some detective work, I realized that the root project did not have the Spring Boot plugin applied.  
Therefore, adding the org.springframework.boot plugin in build.gradle.kts fixed it.  
Okay, so Gradle won’t magically know about Spring Boot tasks without the plugin (but still no idea how it's worked before and at the sudden not, maybe just accidently deleted it...) 🕵️‍♀️

### 2. In-memory storage is tricky
- users disappear on page reload -> sign in works, login fails
- user can vote multiple times in private polls after refreshing
- poll creator info, publish date, deadline are not really stored or linked to users
with using a database this would be easier

### 3. Navigation to look prettier
The app is without routing library,so I added many navigation buttons. It's a bit messy, but they work, however it could be cleaner...

### 4. Missing delete
I don't know is it required or not (at least in this week hm), it just came in mind that I do not have a delete button yet for the poll owner to delete its own poll. However, I think this is not possible without proper authentication, so I just let it go

## Here are listed all the pending issues
- in-memory storage confusion
- real authentication is not yet possible
- Poll metadata (creator, publish date, deadline) is missing
- user can vote multiple times even on private polls
- delete functionality for polls is missing
- strange things happened with bootRun (or just me accidently deleting things)

### Code
👉 [My code for experiments 1-2(-3?)](https://github.com/enikoandrea123/poll-app)


Overall, the app works and is somewhat fun to use, but memory-only storage is confusing and limiting 
