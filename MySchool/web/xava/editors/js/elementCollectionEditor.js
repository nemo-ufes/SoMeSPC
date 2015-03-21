if (elementCollectionEditor == null) var elementCollectionEditor = {};

elementCollectionEditor.onChangeRow = function(element, rowIndex) {
	var currentRow = $(element).parent().parent(); 
	var nextRow = currentRow.next();
	if (nextRow.is(':visible')) return;			
	var newRow = nextRow.clone();
	var token1 = new RegExp("__" + (rowIndex + 1), "g");
	var token2 = "__" + (rowIndex + 2);
	newRow.attr("id", newRow.attr("id").replace(token1, token2));
	var newRowHtml = newRow.html().replace(token1, token2);
	token1 = new RegExp(", " + (rowIndex + 1) + "\\)", "g");
	token2 = ", " + (rowIndex + 2) + ")";
	newRowHtml = newRowHtml.replace(token1, token2);
	newRow.html(newRowHtml);
	var table = currentRow.parent().parent();	
	elementCollectionEditor.setDefaultValues(table, rowIndex); 
	nextRow.show();
	$(nextRow).after(newRow);
	currentRow.children().first().find("a").css('visibility', 'visible');
}

elementCollectionEditor.setDefaultValues = function(table, rowIndex) {
	var header = table.children().first().children().first(); 
	header.children("[id]").each(function() { 
		var headerId = $( this ).attr("id");
		var inputName = headerId.replace(new RegExp("__H", "g"), "__" + rowIndex);
		$("[name='" + inputName + "']").val($( this ).attr("data-default-value")); 		
	});
}

elementCollectionEditor.removeRow = function(application, module, element, rowIndex, hasTotals) { 
	var currentRow = $(element).parent().parent().parent().parent();
	var nextRow = currentRow.next();
	currentRow.remove();
	elementCollectionEditor.renumber(nextRow, rowIndex);
	if (hasTotals) {
		openxava.executeAction(application, module, "", false, "ElementCollection.refreshTotals");
	}	
}

elementCollectionEditor.renumber = function(row, rowIndex) { 
	if (!$(row).is(":visible")) return; 		
	var token1 = new RegExp("__" + (rowIndex + 1), "g");
	var token2 = "__" + rowIndex;
	row.attr("id", row.attr("id").replace(token1, token2));
	var rowHtml = row.html().replace(token1, token2);
	row.html(rowHtml);
	elementCollectionEditor.renumber(row.next(), rowIndex + 1);
}
