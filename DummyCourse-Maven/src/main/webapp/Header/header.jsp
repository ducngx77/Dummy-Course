<jsp:include page="/Header/sidebar.jsp"/>
<jsp:include page="/Header/navbar.jsp"/>

<script>
    window.onscroll = function () {
        scrollFunction()
    };

    function scrollFunction() {
        if (document.body.scrollTop > 10 || document.documentElement.scrollTop > 10) {
            document.getElementById("navbar").style.boxShadow = "2px 2px 5px lightgrey";
        } else {
            document.getElementById("navbar").style.boxShadow = "none";
        }
    }
</script>



