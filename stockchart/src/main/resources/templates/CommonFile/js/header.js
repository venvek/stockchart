const searchBox = document.getElementById("searchBox");
const searchResults = document.getElementById("searchResults");
const form = document.getElementById("stockSearchForm");
const searchBtn = document.getElementById("searchBtn");

// 🔍 자동완성
searchBox.addEventListener("input", async (e) => {
  const query = e.target.value.trim();
  if (!query) {
    searchResults.innerHTML = "";
    searchResults.style.display = "none";
    return;
  }

  const res = await fetch(`/api/search/window2?q=${encodeURIComponent(query)}`);
  const data = await res.json();

  if (data.results.length > 0) {
    let html = "<ul style='list-style:none; padding:0; margin:0; border:1px solid #ccc; background:white; border-radius:6px; box-shadow:0 2px 6px rgba(0,0,0,0.15);'>";
    data.results.forEach(item => {
      html += `
        <li style="padding:8px 10px; cursor:pointer; border-bottom:1px solid #eee;"
            onclick="goToTicker('${item.ticker}')"
            onmouseover="this.style.backgroundColor='#f5f5f5'"
            onmouseout="this.style.backgroundColor='white'">
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

// ✅ 검색 함수 (공용)

async function performSearch() {
  const keyword = searchBox.value.trim();
  if (!keyword) return;

  const res = await fetch(`/api/search/check?tickerOrName=${encodeURIComponent(keyword)}`);
  const data = await res.json();

  if (data.exists) {
    await recordSearch(data.ticker);
    window.location.href = `/stocks?ticker=${encodeURIComponent(data.ticker)}`; // ✅ 정상 이동
  } else {
    // ❌ 없으면 NotFound 페이지로 이동
    window.location.href = `/notfound?ticker=${encodeURIComponent(keyword)}`;
  }
}

// ✅ 버튼 클릭 시
searchBtn.addEventListener("click", (e) => {
  e.preventDefault();
  performSearch();
});

// ✅ 엔터 입력 시
form.addEventListener("submit", (e) => {
  e.preventDefault();
  performSearch();
});

// ✅ 자동완성 클릭 시
async function recordSearch(ticker) {
  try {
    await fetch(`/api/search/record?ticker=${encodeURIComponent(ticker)}`, {
      method: "POST"
    });
  } catch (err) {
    console.error("검색 기록 저장 실패:", err);
  }
}

function goToTicker(ticker) {
  recordSearch(ticker);
  window.location.href = `/stocks?ticker=${encodeURIComponent(ticker)}`;
}

// 💬 모달 표시
function showModal(message) {
  const existing = document.getElementById("notFoundModal");
  if (existing) existing.remove();

  const modal = document.createElement("div");
  modal.id = "notFoundModal";
  modal.innerHTML = `
      <div style="
          position: fixed; top: 0; left: 0; width: 100%; height: 100%;
          background: rgba(0, 0, 0, 0.4);
          display: flex; align-items: center; justify-content: center;
          z-index: 9999;
      ">
          <div style="
              background: white;
              padding: 20px 30px;
              border-radius: 12px;
              text-align: center;
              box-shadow: 0 4px 10px rgba(0,0,0,0.3);
              max-width: 320px;
          ">
              <h3 style="margin-bottom: 10px; color: #333;">❌ 검색 결과 없음</h3>
              <p style="margin-bottom: 20px; color: #666;">${message}</p>
              <button id="modalCloseBtn" style="
                  padding: 8px 16px;
                  background: #007bff;
                  color: white;
                  border: none;
                  border-radius: 6px;
                  cursor: pointer;
              ">확인</button>
          </div>
      </div>
  `;
  document.body.appendChild(modal);
  document.getElementById("modalCloseBtn").addEventListener("click", () => modal.remove());
  modal.addEventListener("click", (e) => { if (e.target === modal) modal.remove(); });
}
