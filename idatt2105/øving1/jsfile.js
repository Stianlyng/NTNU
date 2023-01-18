function toggleVisibility(id) {
    let element = document.getElementById(id);

    if (element.style.display === "none") {
        element.style.display = "block";
    } else {
        element.style.display = "none";
    }
}

function incrementNum(id) {
    let element = document.getElementById(id);
    let num = parseInt(element.innerHTML);
    element.innerHTML = num + 1;
}

function randomArray(id) {
    let array = ["lorem", "ipsum", "dolor", "sit", "amet", "consectetur", "adipiscing", "elit", "sed", "do", "eiusmod", "tempor", "incididunt", "ut", "labore", "et", "dolore", "magna", "aliqua", "ut", "enim", "ad", "minim", "veniam", "quis", "nostrud", "exercitation", "ullamco", "laboris", "nisi", "ut", "aliquip", "ex", "ea", "commodo", "consequat", "duis", "aute", "irure", "dolor", "in", "reprehenderit", "in", "voluptate", "velit", "esse", "cillum", "dolore", "eu", "fugiat", "nulla", "pariatur", "excepteur", "sint", "occaecat", "cupidatat", "non", "proident", "sunt", "in", "culpa", "qui", "officia", "deserunt", "mollit", "anim", "id", "est", "laborum"];
    // ul element with 10 random li from the array,
    let element = document.getElementById(id);
    // remove all li elements
    while (element.firstChild) {
        element.removeChild(element.firstChild);
    }
    for (let i = 0; i < 10; i++) {
        let li = document.createElement("li");
        li.innerHTML = array[Math.floor(Math.random() * array.length)];
        element.appendChild(li);
    }
    
}