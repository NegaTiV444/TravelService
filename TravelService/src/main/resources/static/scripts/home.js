let cities = new Map();

function init() {
    loadCities();
    document.getElementById('btn-add').addEventListener('click', () => addClick(), null);
}

async function addClick() {
    let nameInput = document.getElementById('newCityName');
    let descriptionInput = document.getElementById('newCityDescription');
    let city = {id: 0, name: nameInput.value, description: descriptionInput.value};
    nameInput.value = '';
    descriptionInput.value = '';
    let response = await fetch(window.location.href.substring(0, window.location.href.lastIndexOf("/") + 1) + `api/city`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(city)
    });
    let jsonData = await response.json();
    cities.set(jsonData.id, jsonData);
    printCities();
}

async function updateCities(jsonData) {
    jsonData.forEach(city => cities.set(city.id, city));
    await printCities();
}

async function printCities() {
    if (cities.size > 0) {
        const cityBlock = createCityBlock();
        console.log(cityBlock);
        const recordsListBlock = document.getElementById('city-list');
        recordsListBlock.innerHTML = '';
        recordsListBlock.appendChild(cityBlock);
    }
}

async function loadCities() {
    let url = window.location.href.substring(0, window.location.href.lastIndexOf("/") + 1) + "api/city";
    const request = new Request(url);
    let response = await fetch(request);
    let jsonData = await response.json();
    updateCities(jsonData);
}

function createCityBlock() {
    const recordBlock = document.createDocumentFragment();
    const recordItem = document.getElementById('city-item-template');
    let i = 0;
    cities.forEach(city => {
        const item = recordItem.content.cloneNode(true).querySelector('.city');
        const child = fillTemplate(item, city);
        recordBlock.appendChild(child);
    });
    return recordBlock;
}

async function deleteClick(id) {
    await fetch(window.location.href.substring(0, window.location.href.lastIndexOf("/") + 1) + `api/city/${id}`, {
        method: 'DELETE'
    });
    cities.delete(id);
    printCities();
}

async function updateClick(id) {
    let newDescription = prompt(`Enter the new description for ${cities.get(id).name}:`);
    let city = cities.get(id);
    city.description = newDescription;
    let response = await fetch(window.location.href.substring(0, window.location.href.lastIndexOf("/") + 1) + `api/city`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(city)
    });
    let jsonData = await response.json();
    cities.set(jsonData.id, jsonData)
    printCities();
}

function fillTemplate(item, jsonData) {
    item.querySelector('.id').textContent = jsonData.id + ". ";
    item.querySelector('.name').textContent = jsonData.name + " : ";
    item.querySelector('.description').textContent = jsonData.description;
    item.querySelector('.btn-delete').addEventListener("click", function () {
        deleteClick(jsonData.id);
    }, null);
    item.querySelector('.btn-update').addEventListener("click", function () {
        updateClick(jsonData.id);
    }, null);
    return item;
}

init();