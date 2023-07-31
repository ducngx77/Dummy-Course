let offset;
offset = 20;
let topCoursesOffset = 4;
let lecturerOffset = 4;
let courseByTypeOffset = 0;
const offsetStep = 20;
const topCoursesOffsetStep = 4;
const lecturerOffsetStep = 4;
const courseByTypeOffsetStep = 20;

var radioButtons = document.querySelectorAll('input[name="type"]');

radioButtons.forEach(function (radioButton) {
    radioButton.addEventListener('change', function () {
        if (this.checked) {
            offset = 0;
            let typeID = this.value;
            if (document.getElementById("search-course").value !== "") {
                // print search course value
                offset = 0;
                document.getElementById("course-list").innerHTML = "";
                searchCourse();
                return;
            }
            if (typeID === "all") {
                document.getElementById("course-list").innerHTML = "";
                addCourses();
            } else {

                changeCourseByType(typeID);
            }

        }
    });
});

let searchTextBox = document.getElementById("search-course");

searchTextBox.addEventListener("keyup", function (event) {
    offset = 0;
    document.getElementById("course-list").innerHTML = "";
    searchCourse();
});

function searchCourse() {
    let typeRadio = document.querySelector('input[name="type"]:checked');
    let data = {
        go: "search-course",
        keyword: document.getElementById("search-course").value,
        offset: courseByTypeOffset,
        type: typeRadio.value
    }
    $.ajax({
        url: 'add-courses',
        method: 'GET',
        data: data,
        success: function (response) {
            let courseList = document.getElementById("course-list");
            courseList.innerHTML
                += response;
            //print offset
            offset = courseList.childElementCount;
        },
        error: function (xhr, status, error) {
            // Handle any errors
            console.log(error);
        }
    });
}

function SearchAll() {
    let data = {
        go: "search-all",
        generalKey: document.getElementById("search-input").value,
        offset: courseByTypeOffset,
        type: "all"
    }
    $.ajax({
        url: 'add-courses',
        method: 'GET',
        data: data,
        success: function (response) {
            let searchList = document.getElementById("search-bar-list");
            searchList.innerHTML
                += response;
            //print offset
            offset = searchList.childElementCount;
        },
        error: function (xhr, status, error) {
            // Handle any errors
            console.log(error);
        }
    });
}

let searchAllTextBox = document.getElementById("search-input");

searchAllTextBox.addEventListener("keyup", function (event) {
    offset = 0;
    document.getElementById("search-bar-list").innerHTML = "";
    SearchAll();
});

function changeCourseByType(typeID) {
    let data = {
        go: "get-course-by-type",
        type_id: typeID,
        offset: courseByTypeOffset
    }
    $.ajax({
        url: 'add-courses',
        method: 'GET',
        data: data,
        success: function (response) {
            let courseList = document.getElementById("course-list");
            courseList.innerHTML
                = response;
            //print offset
            offset = courseList.childElementCount;
        },
        error: function (xhr, status, error) {
            // Handle any errors
            console.log(error);
        }
    });
}


function changeComment() {
    let data = {
        go: "change-comment"
    }
    $.ajax({
        url: 'change-comment',
        method: 'GET',
        data: data,
        success: function (response) {
            var feedback = document.getElementById("feedback-content");
            feedback.innerHTML
                += response;
        },
        error: function (xhr, status, error) {
            // Handle any errors
            console.log(error);
        }
    });

}

function addCourses() {
    let courses = document.getElementById("course-list");
    let typeRadio = document.querySelector('input[name="type"]:checked');

    if (typeRadio && typeRadio.value !== "all") {
        let type = typeRadio.value;
        $.ajax({
            url: 'add-courses',
            method: 'GET',
            data: {
                go: "add-courses-by-type",
                offset: offset,
                type: type
            },
            success: function (response) {
                courses.innerHTML
                    += response;
                offset += offsetStep;
                console.log(offset);
            },
            error: function (xhr, status, error) {
                // Handle any errors
                console.log(error);
            }
        })
    } else {
        $.ajax({
            url: 'add-courses',
            method: 'GET',
            data: {
                go: "add-courses",
                offset: offset
            },
            success: function (response) {
                courses.innerHTML
                    += response;
                offset += offsetStep;
                console.log(offset);
            },
            error: function (xhr, status, error) {
                // Handle any errors
                console.log(error);
            }
        })
    }

}

function iterateTopCourse() {
    let topCourses = document.getElementById("top-course-list");
    $.ajax({
        url: 'iterate-top-courses',
        method: 'GET',
        data: {
            go: "iterate-top-courses",
            offset: topCoursesOffset
        },
        success: function (response) {
            topCourses.innerHTML
                += response;
            topCoursesOffset += topCoursesOffsetStep;
            console.log(topCoursesOffsetStep);
        },
        error: function (xhr, status, error) {
            // Handle any errors
            console.log(error);
        }
    })
}

function iterateLecturer() {
    let lecturerList = document.getElementById("lecturer-list");
    $.ajax({
        url: 'iterate-lecturer',
        method: 'GET',
        data: {
            go: "iterate-lecturer",
            offset: lecturerOffset
        },
        success: function (response) {
            lecturerList.innerHTML
                += response;
            lecturerOffset += lecturerOffsetStep;
        },
        error: function (xhr, status, error) {
            // Handle any errors
            console.log(error);
        }
    })
}