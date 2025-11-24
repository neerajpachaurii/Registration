// webapp/js/dashboard.js
document.addEventListener('DOMContentLoaded', function () {
  // DOM refs
  const tableBody = document.getElementById('projectsTableBody');
  const rowsNodeList = Array.from(tableBody.querySelectorAll('tr'));
  const filterTitle = document.getElementById('filterTitle');
  const filterOwner = document.getElementById('filterOwner');
  const filterDesc = document.getElementById('filterDesc');
  const filterHasFile = document.getElementById('filterHasFile');
  const pageSizeSelect = document.getElementById('pageSize');
  const btnClear = document.getElementById('btnClear');
  const rowCountEl = document.getElementById('rowCount');
  const filterInfo = document.getElementById('filterInfo');

  // pagination refs
  const firstPageBtn = document.getElementById('firstPage');
  const prevPageBtn = document.getElementById('prevPage');
  const nextPageBtn = document.getElementById('nextPage');
  const lastPageBtn = document.getElementById('lastPage');
  const currentPageInput = document.getElementById('currentPage');
  const totalPagesEl = document.getElementById('totalPages');
  const validationAlert = document.getElementById('validationAlert');

  let allRows = rowsNodeList.map(r => {
    return {
      el: r,
      title: (r.dataset.title || '').toLowerCase(),
      owner: (r.dataset.owner || '').toLowerCase(),
      desc: (r.dataset.desc || '').toLowerCase(),
      hasFile: (r.dataset.hasfile || 'false') === 'true'
    };
  });

  let filtered = allRows.slice();
  let pageSize = parseInt(pageSizeSelect.value, 10) || 10;
  let currentPage = 1;
  let totalPages = 1;

  // helpers
  function escapeRegExp(s) {
    return s.replace(/[.*+?^${}()|[\]\\]/g, '\\$&');
  }

  function applyFilters() {
    const t = filterTitle.value.trim().toLowerCase();
    const o = filterOwner.value.trim().toLowerCase();
    const d = filterDesc.value.trim().toLowerCase();
    const hasFileChecked = filterHasFile.checked;

    filtered = allRows.filter(row => {
      if (t && !row.title.includes(t)) return false;
      if (o && !row.owner.includes(o)) return false;
      if (d && !row.desc.includes(d)) return false;
      if (hasFileChecked && !row.hasFile) return false;
      return true;
    });

    // Reset to first page when filters change
    currentPage = 1;
    render();
    // show filter summary
    const parts = [];
    if (t) parts.push(`Title contains "${t}"`);
    if (o) parts.push(`Owner contains "${o}"`);
    if (d) parts.push(`Description contains "${d}"`);
    if (hasFileChecked) parts.push('Has file');
    filterInfo.textContent = parts.length ? ('Filters: ' + parts.join(' · ')) : 'No filters applied.';
  }

  function clearHighlights(el) {
    // remove highlight spans inserted previously
    el.querySelectorAll('.highlight').forEach(h => {
      const parent = h.parentNode;
      parent.replaceChild(document.createTextNode(h.textContent), h);
      parent.normalize();
    });
  }

  function highlightText(el, query) {
    if (!query) return;
    const text = el.textContent;
    const regex = new RegExp(escapeRegExp(query), 'gi');

    // simple approach: replace innerHTML (safe because data came from server; however we used escapeHtml)
    const newHTML = text.replace(regex, match => `<span class="highlight">${match}</span>`);
    el.innerHTML = newHTML;
  }

  function render() {
    // hide all rows initially
    allRows.forEach(r => r.el.style.display = 'none');

    pageSize = parseInt(pageSizeSelect.value, 10) || 10;
    totalPages = Math.max(1, Math.ceil(filtered.length / pageSize));
    // validate currentPage
    if (isNaN(currentPage) || currentPage < 1) currentPage = 1;
    if (currentPage > totalPages) currentPage = totalPages;

    // compute slice
    const start = (currentPage - 1) * pageSize;
    const end = start + pageSize;
    const pageRows = filtered.slice(start, end);

    // show rows on the page
    pageRows.forEach(item => {
      // reset any previous highlights
      clearHighlights(item.el);
      // highlight filter matches (simple: title + owner + desc)
      const qTitle = filterTitle.value.trim();
      const qOwner = filterOwner.value.trim();
      const qDesc = filterDesc.value.trim();

      if (qTitle) highlightText(item.el.cells[0], qTitle);
      if (qDesc) highlightText(item.el.cells[1], qDesc);
      if (qOwner) highlightText(item.el.cells[2], qOwner);

      item.el.style.display = '';
    });

    // update pagination UI
    currentPageInput.value = currentPage;
    totalPagesEl.textContent = `/ ${totalPages}`;
    rowCountEl.textContent = `${filtered.length} project(s)`;

    // enable/disable buttons
    firstPageBtn.disabled = currentPage === 1;
    prevPageBtn.disabled = currentPage === 1;
    nextPageBtn.disabled = currentPage === totalPages;
    lastPageBtn.disabled = currentPage === totalPages;

    // clear validation messages
    validationAlert.innerHTML = '';
  }

  // events
  [filterTitle, filterOwner, filterDesc].forEach(i => i.addEventListener('input', debounce(applyFilters, 300)));
  filterHasFile.addEventListener('change', applyFilters);
  pageSizeSelect.addEventListener('change', function () { currentPage = 1; render(); });

  btnClear.addEventListener('click', function () {
    filterTitle.value = '';
    filterOwner.value = '';
    filterDesc.value = '';
    filterHasFile.checked = false;
    pageSizeSelect.value = '10';
    applyFilters();
  });

  firstPageBtn.addEventListener('click', function () { currentPage = 1; render(); });
  prevPageBtn.addEventListener('click', function () { if (currentPage > 1) { currentPage--; render(); } });
  nextPageBtn.addEventListener('click', function () { if (currentPage < totalPages) { currentPage++; render(); } });
  lastPageBtn.addEventListener('click', function () { currentPage = totalPages; render(); });

  currentPageInput.addEventListener('change', function () {
    const val = parseInt(currentPageInput.value, 10);
    if (isNaN(val) || val < 1 || val > totalPages) {
      showValidation(`Invalid page number. Enter a number between 1 and ${totalPages}.`);
      currentPageInput.value = currentPage;
      return;
    }
    currentPage = val;
    render();
  });

  // small debounce utility
  function debounce(fn, wait) {
    let t;
    return function () {
      clearTimeout(t);
      const args = arguments;
      t = setTimeout(() => fn.apply(this, args), wait);
    };
  }

  function showValidation(msg) {
    validationAlert.innerHTML = `<div class="alert alert-warning py-1 px-2">${msg}</div>`;
  }

  // initial populate
  applyFilters(); // this will call render()
});
