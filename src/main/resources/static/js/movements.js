/**
 * 
 */

if ($('.movementElement').length > 0) {
	var movementList = new List('movementsTable', {
		valueNames: [{ name: 'date', attr: 'data-timestamp' }, 'productType', 'mode', 'quantity', 'unit', 'unitPrice', 'amount', 'type'],
		page: 10000,
		pagination: [{
			paginationClass: "pagination",
			innerWindow: 1,
			right: 0,
			left: 0
		}]
	});

	if ((movementList.size() / movementList.page) <= 1) {
		$('.pagination').hide();
	}
}

$(document).ready(function() {
	$("#btnExport").click(function() {

		$("#movementsTable").table2excel({
			exclude: ".noExport",
			filename: "CariHareketler.xls",
		});
	});

	$("#btnPrint").click(function() {
		var tempTable = $("#movementsTable").html();

		$("#movementsTable .noExport").remove();

		var printWindow = window.open('');
		printWindow.document.write('<html><head><title>Cari Hesap Takip</title>');
		printWindow.document.write('<link rel="stylesheet" href="/css/print.css">');
		printWindow.document.write('</head>');

		printWindow.document.write('<body>');

		var divContents = document.getElementById("movementsTable").innerHTML;
		printWindow.document.write(divContents);
		printWindow.document.write('</body>');

		printWindow.document.write('</html>');
		printWindow.document.close();

		$("#movementsTable").html(tempTable);
	});

});