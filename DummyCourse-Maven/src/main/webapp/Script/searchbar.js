let searchBar = document.getElementById("search-bar-content");
let searchIcon = document.getElementById("search-icon-content");
let confirmSearchButton = document.getElementById("confirm-search-button");
searchIcon.onclick = function () {
    searchBar.classList.toggle("search-active-content");
    confirmSearchButton.classList.toggle("confirm-search-button-active");
}
