const searchBox = document.getElementById("searchBox");
const searchResults = document.getElementById("searchResults");
const form = document.getElementById("stockSearchForm");

// 입력 시 자동완성 표시
searchBox.addEventListener("input", async (e) => {
    const query = e.target.value.trim();
    if (!query) {
        searchResults.innerHTML = "";
        searchResults.style.display = "none";
        return;
    }

    const res = await fetch(`/api/search/window?q=${encodeURIComponent(query)}`);
    const data = await res.json();

    let html = "";
    if (data.results.length > 0) {
        html += "<ul style='list-style:none; padding:0; margin:0;'>";
        data.results.forEach(item => {
            html += `
                <li style="padding: 6px; cursor: pointer;"
                    onclick="goToTicker('${item.ticker}')">
                    <strong>${item.ticker}</strong> – ${item.companyName}
                </li>`;
        });
        html += "</ul>";
        searchResults.innerHTML = html;
        searchResults.style.display = "block";
    } else {
        searchResults.style.display = "none";
    }
});

// 자동완성 선택 시 이동
function goToTicker(ticker) {
    window.location.href = `/stocks/${encodeURIComponent(ticker)}`;
}

// 검색창 직접 입력 후 엔터 → 이동
form.addEventListener("submit", function(event) {
    event.preventDefault();
    const ticker = searchBox.value.trim();
    if (ticker) {
        window.location.href = `/stocks/${encodeURIComponent(ticker)}`;
    }
});