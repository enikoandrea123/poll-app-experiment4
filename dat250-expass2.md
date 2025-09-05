# Poll app project report


### Set up and installation
I set up a Spring Boot and I configured the application with controllers, entities, and repositories for users, polls, options, and votes.  
For testing, I used JUnit 5, Spring Boot Test, and MockMvc with @SpringBootTest and @AutoConfigureMockMvc.
I used a while ago these technology, so tried to remember and searching up for the forgotten parts.

## Issues
Of course, I encountered many 🙃

### First issue
#### JSON parsing
After implementing `poll-scenarios.http`, I tried running requests manually, but they always failed even when the request and response seemed correct.
Finally, I checked the error message carefully and I discovered that I was parsing an already parsed JSON object. So I learned by now that Spring automatically deserializes JSON into objects by default.
Therefore, I removed the extra parsing logic, and the requests started working magically.✨😅

### Second issue
Again for automated testing, I chose MockMvc.
At first, I encountered problems with casting response data and retrieving fields like `id`.
I got error like:
- `Cannot resolve method 'andExpect'`
- `Status expected:<201> but was:<400>`
- `Status expected:<201> but was:<404>`
So slowly started to search for the causes of my headache. And solved the issues, which were actually minor with mistyping.
Like:
- Playloads isn't included all required fields. I missed `isPublic` and `allowSingleVotePerUser` since I implemented them later on.
- Endpoint path needed correction, because of missing slashes.
- And then the confusing conversion issue which take a while to understand and solve as always.But in the end I used `ObjectMapper` to convert response JSON into a `Map` and then extracted values with `(Number) map.get("id")`. And this fixed all of my problems for a while 😀. 

### Pending issues 
My code builds and runs, but it could be nicer and more professional of course. In theory, I know that it would be nicer to use helper methods and smaller independent ones. 
But mhy integration test currently handles the whole workflow in one method. (however, keept the experimental methods).
I attempted splitting it into multiple smaller tests, but faced state-reset problems between tests (and don't want to mess up my working code).
Therefore, I kept the full workflow in a single test. 

