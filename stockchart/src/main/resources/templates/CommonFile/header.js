/**
 * 
 */
document.addEventListener("DOMContentLoaded", function() {
    const form = document.getElementById("stockSearchForm");
    const searchBox = document.getElementById("searchBox");

    if (form && searchBox) {
        form.addEventListener("submit", function(event) {
            event.preventDefault();
            const ticker = searchBox.value.trim();
            if (ticker) {
                window.location.href = "/stocks/" + encodeURIComponent(ticker);
            }
        });
    }
});