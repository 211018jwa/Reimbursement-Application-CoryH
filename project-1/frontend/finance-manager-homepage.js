// Check to see if the user is logged in or not, and if not, relocate them back to 
//login screeen
window.addEventListener('load', async () => {

    let res = await fetch('http://localhost:8080/checkloginstatus', {
        credentials: 'include',
        method: 'GET'

    });

    if (res.status === 200) {
        let userObj = await res.json();

        if (userObj.userRole === 'Employee') {
            window.location.href = 'employee-homepage.html';

        }
    } else if (res.status === 401) {
        window.location.href = 'index.html';
    }

    // If we make it past the authorization checks, call another function that will 
    // retrieve all reimbursements
    populateTableWithReimbursements();

});

async function populateTableWithReimbursements() {
    let res = await fetch('http://localhost:8080/reimbursements', {
        credentials: 'include',
        method: 'GET'
    });

    let tbodyElement = document.querySelector('#reimbursement-table tbody');
    tbodyElement.innerHTML = '';

    let reimbursementArray = await res.json();

    for (let i = 0; i < reimbursementArray.length; i++) {
       let reimbursement = reimbursementArray[i];

       let tr = document.createElement('tr');

       let td1 = document.createElement('td');
       td1.innerHTML = reimbursement.reimbId;

       let td2 = document.createElement('td');
       td2.innerHTML = '$' + reimbursement.reimbAmount;

       let td3 = document.createElement('td');
       td3.innerHTML = new Date(reimbursement.submitted).toUTCString();
        
       let td9 = document.createElement('td'); // Finance Manager Id
       let td4 = document.createElement('td'); // Status of Reimbursement
       let td10 = document.createElement('td');
       let td11 = document.createElement('td');
       if (reimbursement.resolver != 0) {
        td4.innerHTML = reimbursement.status;
        td9.innerHTML = reimbursement.resolver;
       } else { // Display the below content
           td4.innerHTML = 'Pending';
           td9.innerHTML = '-';

           // Main challenge here is linking each button with a particular parameter
           // (reimbursement id that we want to change the grade of)
           let statusInput = document.createElement('input');
           statusInput.setAttribute('type', String);
           let statusButton = document.createElement('button');
           statusButton.innerText = 'Status';
           statusButton.addEventListener('click', async () => {
               
           let res =  await fetch(`http://localhost:8080/reimbursements/${reimbursement.reimbId}/updateReimbursement`,
              {
                    credentials: 'include',
                    method: 'PATCH',
                    body: JSON.stringify({
                        status: statusInput.value
                   })
                });
               
                if (res.status === 200) {
                    populateTableWithReimbursements();
                }

           })
           td10.appendChild(statusButton);
           td11.appendChild(statusInput);

       }
       

       let td5 = document.createElement('td');
       td5.innerHTML = reimbursement.resolved;

       let td6 = document.createElement('td');
       td6.innerHTML = reimbursement.type;

       let td7 = document.createElement('td');
       td7.innerHTML = reimbursement.description;

       let td8 = document.createElement('td');
       td8.innerHTML = reimbursement.author;

       let td12 = document.createElement('td');
       // td12.innerHTML = reimbursement.receipt;

       let viewImageButton = document.createElement('button');
       viewImageButton.innerHTML = 'View Image';
       td12.appendChild(viewImageButton);

       viewImageButton.addEventListener('click', async () => {
            // Add the is active class to the modal ( so that the modal appears)
            // inside of the modal on the div.modal-content 
            // -> img tag with src="http://localhost:8080/reimbursements/{reimb_id}/image" 
            let reimbursementImageModal = document.querySelector('#reimbursement-image-modal');

            let modalCloseElement = reimbursementImageModal.querySelector('button');
            modalCloseElement.addEventListener('click', () => {
                reimbursementImageModal.classList.remove('is-active');
            });

            //you can take an element and use querySelector on it to find the child element that are nested within it 
            let modalContentElement = reimbursementImageModal.querySelector('.modal-content');
            modalContentElement.innerHTML = '';

            let imageElement = document.createElement('img');
            imageElement.setAttribute('src', `http://localhost:8080/reimbursements/${reimbursement.reimbId}/image`);
            modalContentElement.appendChild(imageElement);

            reimbursementImageModal.classList.add('is-active');

       });


       
       

       tr.appendChild(td1);
       tr.appendChild(td2);
       tr.appendChild(td3);
       tr.appendChild(td4);
       tr.appendChild(td5);
       tr.appendChild(td6);
       tr.appendChild(td7);
       tr.appendChild(td8);
       tr.appendChild(td9);
       tr.appendChild(td10);
       tr.appendChild(td11);
       tr.appendChild(td12);


       tbodyElement.appendChild(tr);

    }
}



let logoutBtn = document.querySelector('#logout');

logoutBtn.addEventListener('click', async () => {

    let res = await fetch('http://localhost:8080/logout', {
        'method': 'POST',
        'credentials': 'include' 
    });

    if (res.status === 200) {
        window.location.href = 'index.html';
    }
})

