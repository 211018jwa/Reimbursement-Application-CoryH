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
       td3.innerHTML = timeConverter(reimbursement.submitted);
       
       let td4 = document.createElement('td'); // Status of Reimbursement
       td4.innerHTML = reimbursement.status; 
       
       let td5 = document.createElement('td');
       td5.innerHTML = reimbursement.resolved;

       let td6 = document.createElement('td');
       td6.innerHTML = reimbursement.type;


       let td7 = document.createElement('td');
       td7.innerHTML = reimbursement.description;

       let td8 = document.createElement('td');
       td8.innerHTML = reimbursement.author;

       let td9 = document.createElement('td'); // Finance Manager Id
         if (reimbursement.resolver != 0) {
        td9.innerHTML = reimbursement.resolver;
        } else {
           td9.innerHTML = '-';
        }

       let td10 = document.createElement('td');
       let viewImageButton = document.createElement('button');
       viewImageButton.innerHTML = 'View Image';
       td10.appendChild(viewImageButton);

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

       tbodyElement.appendChild(tr);

    }
}

let reimbursementSubmitButton = document.querySelector('.submit-reimb-btn');

reimbursementSubmitButton.addEventListener('click', submitReimbursement);

async function submitReimbursement() {

    let reimbursmentAmountInput = document.querySelector('.reimbursement-amount');
    let reimbursementImageInput = document.querySelector('.reimbursement-file');
    let reimbursementDropDwn = document.querySelector('#reimbType');
    let value = reimbursementDropDwn.options[reimbursementDropDwn.selectedIndex].textContent;
    let reimbursmentDescriptionInput = document.querySelector('.reimbursement-description');
    
    const file = reimbursementImageInput.files[0];

    let formData = new FormData();
    formData.append('reimb_amount', reimbursmentAmountInput.value);
    formData.append('reimb_receipt', file);
    formData.append('reimb_type', value);
    formData.append('reimb_description', reimbursmentDescriptionInput.value);

   let res = await fetch('http://localhost:8080/reimbursements', {
       method: 'POST',
        credentials: 'include',
        body: formData
    });

    if (res.status === 201) {
        populateTableWithReimbursements();
    } else if (res.status === 400) {
        let reimbursementForm = document.querySelector('.reimbursement-submit-form');

        let data = await res.json();

        let pTag = document.createElement('p');
        pTag.innerHTML = data.message;
        pTag.style.color = 'red';

        reimbursementForm.appendChild(pTag);
    }


}

function timeConverter(UNIX_timestamp){
    var a = new Date(UNIX_timestamp);
    var months = ['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'];
    var year = a.getFullYear();
    var month = months[a.getMonth()];
    var date = a.getDate();
    var hour = a.getHours();
    var min = a.getMinutes();
    var time = date + ' ' + month + ' ' + year + ' ' + hour + ':' + min;
    return time;
  }

