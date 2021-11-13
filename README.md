# MessageApplication

- In project root in terminal:
    - Run : *__docker-compose up -d__* or *__docker-compose up__*
    - Access application at `http://localhost:6868/`
    
Endpoints: `/login` and `/persons/register` available for everybody.
Endpoint `"/messages"` available for users who was registered and authenticated.
To save message send POST request with JSON :

*{  
   "username":"your-user-name",
   "message":"some-message"
}*

To take messages from actual user send GET request with JSON :

*{ 
   "username":"your-user-name",
   "message":"history __number__"
}*

Where *__number__* is number of last messages you want to obtain.