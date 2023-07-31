window.onscroll = function () {
    scrollFunction()
};

function scrollFunction() {
    if (document.body.scrollTop > 10 || document.documentElement.scrollTop > 10) {
        document.getElementById("navbar").style.boxShadow = "0px 0px 5px rgba(0, 0, 0, 0.3)";
    } else {
        document.getElementById("navbar").style.boxShadow = "none";
    }
}

const nav = document.getElementById('navbar');
const screenWidth = window.innerWidth;
const leftValue = (screenWidth - 1400) / 2;
nav.style.left = leftValue + 'px';

function getRandomColor(courseID) {
    var remainder = courseID % 5;
    var colors = ["#FF0000", "#00FF00", "#0000FF", "#FFFF00", "#00FFFF"];
    return colors[remainder];
}
//
// document.addEventListener("click", function (event) {
//     let popup =  document.getElementById("search-box")
//     let isClickInsidePopup = popup.contains(event.target);
//     let check = document.getElementById("search-checkbox-input")
//     if (!isClickInsidePopup && popup.style.display != "none") {
//         check.checked = false;
//     }
// })



