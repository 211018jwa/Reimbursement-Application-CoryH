// Whenever the login page loads, check if they are logged in or not
// If they are, simply check what their role is by sending an http request
// GET /checkloginstatus -> User object which has a userRole property
// IF they are not logged in, contine to stay on the login page
window.addEventListener('load', async () => {

    console.log('EXECUTED');

    let res = await fetch('http://localhost:8080/checkloginstatus', {
        method: 'GET',
        credentials: 'include'
    });
    // If the above request results in a 200 status code, that means that we are actually logged in
    // So we need to the take the userRole information and determine where to redirect them to
    if (res.status === 200) {
        let userObj = await res.json();
        
        if (userObj.userRole === 'Employee'){
            window.location.href = 'employee-homepage.html';
        } else if (userObj.userRole === 'Financial Manager'){
            window.location.href = 'finance-manager-homepage.html';
        }
    }
});


// We need to grab our button element
// Login funtionality

let loginButton = document.querySelector('#login-btn');

// Specifying what function to execute when button is clicked

loginButton.addEventListener('click', loginButtonClicked);

function loginButtonClicked() {
    login();

}

async function login() {
    let usernameInput = document.querySelector('#username');
    let passwordInput = document.querySelector('#password');
try {
    let res = await fetch('http://localhost:8080/login', {
        method: "POST",
        credentials: 'include', // This is to make sure that the browser retains the cookie and includes it in future request
        body: JSON.stringify({
            username: usernameInput.value,
            password: passwordInput.value
        }) // JSON.stringify will take the JSON object and turn it into a string 
    });

    let data = await res.json();

    //Check if login incorrect or not 
    if (res.status === 400) {
      let loginErrorMessage = document.createElement('p');
      let loginDiv = document.querySelector('#login-info');

      
      loginErrorMessage.innerHTML = data.message;
      loginErrorMessage.style.color = 'red';
      loginDiv.appendChild(loginErrorMessage);
    }

    if (res.status === 200) {
        console.log(data.userRole);
        if (data.userRole === 'Employee') {
            // redirect to the employee homepage
            window.location.href = 'employee-homepage.html'
        } else if (data.userRole === 'Financial Manager') {
            window.location.href = 'finance-manager-homepage.html'
        }
    }
 
    
  } catch(err) {
      console.log(err);

  }

}
