var selected=false;

function deleteRecords(event) {
    var records=document.getElementsByClassName("record");
    var ids=document.getElementById("ids");
    ids.value='';
    for (var i=0;i<records.length;i++) {
        if (records[i].checked) {
            ids.value+=records[i].value;
        }
        if (i!=records.length-1) ids.value+=',';
    }
    if (!confirm(confirmMessage)) {
        event.preventDefault();
    }
}

function selectAll() {
    selected=!selected;
    var records=document.getElementsByClassName("record");
    for (var i=0;i<records.length;i++) {
        records[i].checked=selected;
    }
}