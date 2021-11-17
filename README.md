# MessageApplication

- In project root in terminal:
    - Run : *__docker-compose up -d__* or *__docker-compose up__*
    - Access application at `http://localhost:6868/`
    
Endpoints: `/login` and `/persons/register` available for everybody.
Endpoint `/messages` available for users who was registered and authenticated.

To test API you can use __Postman__.  
After service is running, Database contains some users: username - *__"John"__*, *__"Maya"__* and thier passwords - *__"pass"__*.You can skip registration and try authentication and message service.

*__Examples:__*
 - To __Register__ new *User* send to URL `http://localhost:6868/persons/register` POST request with JSON in body:  
  &nbsp;&nbsp;  *__{  
  &nbsp;&nbsp;&nbsp;&nbsp;      "username":"Bob",  
  &nbsp;&nbsp;&nbsp;&nbsp;      "password":"qwerty"  
  &nbsp;&nbsp; }__*
  
      Now the "Bob" in DataBase.

 - To __Authenticate__ and obtain token send to URL `http://localhost:6868/login` POST request with JSON in body:  
&nbsp;&nbsp;    *__{  
&nbsp;&nbsp;&nbsp;&nbsp;        "username":"Bob",  
&nbsp;&nbsp;&nbsp;&nbsp;        "password":"qwerty"  
&nbsp;&nbsp;    }__*

    If the Bob in Database and password is correct in response body will be JSON with jwt like:  
&nbsp;&nbsp;    *__{  
&nbsp;&nbsp;&nbsp;&nbsp;"access_jwt":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYW1lIjoiQm9iIiwiaW
&nbsp;&nbsp; &nbsp;&nbsp;0IjoxNTE2MjM5MDIyfQ.jfSeukSk8gu-FkZpf1RbFJAxVZQet3PMoMojhjuejuE"  
&nbsp;&nbsp;}__*

    Now to __Authorized__ use jwt above in header - *Authorization: Bearer  eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYW1lIjoiQm9iIiwiaW0IjoxNTE2MjM5MDIyfQ.jfSeukSk8gu-FkZpf1RbFJAxVZQet3PMoMojhjuejuE*
    
 - To __Save__ message send to URL `http://localhost:6868/messages` POST request, add header *Authorization:Bearer "token taht was sent in a previous step"* and JSON in the body like:  
&nbsp;&nbsp;*__{  
&nbsp;&nbsp;&nbsp;&nbsp;   "username":"Bob",  
&nbsp;&nbsp;&nbsp;&nbsp;   "message":"I want to break free"  
&nbsp;&nbsp;}__*

    The response will contain in th body the message that you send in JSON format and response code __201__
 - To __Get__ last messages of *User* send to URL `http://localhost:6868/messages` GET request, add header *Authorization:Bearer "token that was sent in a previous step"* and JSON in the body like:  
&nbsp;&nbsp;*__{  
&nbsp;&nbsp;&nbsp;&nbsp;   "username":"Bob",  
&nbsp;&nbsp;&nbsp;&nbsp;   "message":"history 10"  
&nbsp;&nbsp;}__*

    In example the response will contain list of 10 messages(or less if Bob doesn't have 10 messages in history) in JSON formats. You can put any positive number after word *__"history"__*.
    
