window.addEventListener('load', async () => {

    let res = await fetch('http://localhost:8080/checkloginstatus', {
        credentials: 'include',
        method: 'GET'

    });

    if (res.status === 200) {
        let userObj = await res.json();

        if (userObj.userRole === 'Financial Manager') {
            window.location.href = 'finance-manager-homepage.html';

        }
    } else if (res.status === 401) {
        window.location.href = 'index.html';
    }

    populateTableWithReimbursements();

});

async function populateTableWithReimbursements() {
    let res = await fetch('http://localhost:8080/reimbursements', {
        credentials: 'include',
        method: 'GET'
    });

    let tbodyElement = document.querySelector('#reimbursement-table tbody');
    let reimbursementArray = await res.json();

    for (let i = 0; i < reimbursementArray.length; i++) {
       let reimbursement = reimbursementArray[i];

       let tr = document.createElement('tr');

       let td1 = document.createElement('td');
       td1.innerHTML = reimbursement.reimbId;

       let td2 = document.createElement('td');
       td2.innerHTML = reimbursement.reimbAmount;

       let td3 = document.createElement('td');
       td3.innerHTML = reimbursement.submitted;
        
       let td9 = document.createElement('td'); // Finance Manager Id
       let td4 = document.createElement('td'); // Status of Reimbursement
       if (reimbursement.resolver != 0) {
        td4.innerHTML = reimbursement.status;
        td9.innerHTML = reimbursement.resolver;
       } else {
           td4.innerHTML = 'Pending';
           td9.innerHTML = '-';
       }
       

       let td5 = document.createElement('td');
       td5.innerHTML = reimbursement.resolved;

       let td6 = document.createElement('td');
       td6.innerHTML = reimbursement.type;

       let td7 = document.createElement('td');
       td7.innerHTML = reimbursement.description;

       let td8 = document.createElement('td');
       td8.innerHTML = reimbursement.author;

       
       

       tr.appendChild(td1);
       tr.appendChild(td2);
       tr.appendChild(td3);
       tr.appendChild(td4);
       tr.appendChild(td5);
       tr.appendChild(td6);
       tr.appendChild(td7);
       tr.appendChild(td8);
       tr.appendChild(td9);

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