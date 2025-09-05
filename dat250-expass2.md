# Poll app project report

technical problems that you encountered during installation and how you resolved

any pending issues with this assignment that you did not manage to solve_

### First issue
#### JSON parsing
After implemented *poll-scenarios.http*, I cannot run it manually. I mean it always failed even with the right request and responses. In the end I figured it out from the error, that I parsed the already parsed JSON (I didn't know that it is a default thing), therefore I just needed to remove the parsing from my code.

### Second issue
For Automate testing, I choosed MvcMock since I used it before during my previous studies. However, I encountered a problem with casting and finding the righ

