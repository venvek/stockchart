const searchBox = document.getElementById("searchBox");
const searchResults = document.getElementById("searchResults");
const form = document.getElementById("stockSearchForm");

// 자동완성
searchBox.addEventListener("input", async (e) => {
    const query = e.target.value.trim();
    if (!query) {
        searchResults.innerHTML = "";
        searchResults.style.display = "none";
        return;
    }

    const res = await fetch(`/api/search/window?q=${encodeURIComponent(query)}`);
    const data = await res.json();

    if (data.results.length > 0) {
        let html = "<ul style='list-style:none; padding:0; margin:0;'>";
        data.results.forEach(item => {
            html += `
                <li style="padding: 6px; cursor: pointer;"
                    onclick="goToTicker('${item.ticker}')">
                    <strong>${item.ticker}</strong> / ${item.companyName}
                </li>`;
        });
        html += "</ul>";
        searchResults.innerHTML = html;
        searchResults.style.display = "block";
    } else {
        searchResults.innerHTML = "";
        searchResults.style.display = "none";
    }
});

// 클릭 시 이동
function goToTicker(ticker) {
    window.location.href = `/stocks/${encodeURIComponent(ticker)}`;
}

// 엔터 입력 시 처리
form.addEventListener("submit", async (event) => {
    event.preventDefault();
    const keyword = searchBox.value.trim();
    if (!keyword) return;

    // 서버에 해당 회사나 티커가 존재하는지 확인
    const res = await fetch(`/api/search/check?tickerOrName=${encodeURIComponent(keyword)}`);
    const data = await res.json();

    if (data.exists) {
        window.location.href = `/stocks/${encodeURIComponent(data.ticker)}`;
    } else {
        window.location.href = `/notfound`;
    }
});
