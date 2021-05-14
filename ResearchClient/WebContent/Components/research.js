$(document).ready(function () {
    if ($("#alertSuccess").text().trim() == "") {
        $("#alertSuccess").hide();
    }
    $("#alertError").hide();
});



$(document).on("click", "#addresearch", function (event) {
    // Clear alerts---------------------
    $("#alertSuccess").text("");
    $("#alertSuccess").hide();
    $("#alertError").text("");
    $("#alertError").hide();
    // Form validation-------------------
    var status = validateResearchForm();
    if (status != true) {
        $("#alertError").text(status);
        $("#alertError").show();
        return;
    }
    // If valid------------------------
    var type = ($("#hiddenResearchIDSave").val() == "") ? "POST" : "PUT";
    $.ajax(
        {
            url: "ResearchClientAPI",
            type: type,
            data: $("#researchform").serialize(),
            dataType: "text",
            complete: function (response, status) {
                onResearchSaveComplete(response.responseText, status);
            }
         }); 
});

function onResearchSaveComplete(response, status) {
    if (status == "success") {
        var resultSet = JSON.parse(response);
        if (resultSet.status.trim() == "successfull") {
            $("#alertSuccess").text("Successfully saved.");
            $("#alertSuccess").show();
            $("#divResearchGrid").html(resultSet.data);
        } else if (resultSet.status.trim() == "error") {
            $("#alertError").text(resultSet.data);
            $("#alertError").show();
        }
    } else if (status == "error") {
        $("#alertError").text("Error while saving!!");
        $("#alertError").show();
    } else {
        $("#alertError").text("Unknown error while saving!!");
        $("#alertError").show();
    }
    $("#hiddenResearchIDSave").val("");
    $("#researchform")[0].reset();
}

$(document).on("click", ".btnUpdate", function (event) {
    $("#hiddenResearchIDSave").val($(this).data("researchid"));
    $("#researcher_id").val($(this).closest("tr").find('td:eq(1)').text());
    $("#research_name").val($(this).closest("tr").find('td:eq(2)').text());
    $("#description").val($(this).closest("tr").find('td:eq(3)').text());
    $("#category").val($(this).closest("tr").find('td:eq(4)').text());
    $("#expec_budg").val($(this).closest("tr").find('td:eq(5)').text());
});

$(document).on("click", ".btnDelete", function (event) {
    $.ajax(
        {
            url: "ResearchClientAPI",
            type: "DELETE",
            data: "researchID=" + $(this).data("researchid"),
            dataType: "text",
            complete: function (response, status) {
                onResearchDeleteComplete(response.responseText, status);
            }
        });
});

function onResearchDeleteComplete(response, status) {
    if (status == "success") {
        var resultSet = JSON.parse(response);
        if (resultSet.status.trim() == "successfull") {
            $("#alertSuccess").text("Successfully deleted.");
            $("#alertSuccess").show();
            $("#divResearchGrid").html(resultSet.data);
        } else if (resultSet.status.trim() == "error") {
            $("#alertError").text(resultSet.data);
            $("#alertError").show();
        }
    } else if (status == "error") {
        $("#alertError").text("Error while deleting.");
        $("#alertError").show();
    } else {
        $("#alertError").text("Unknown error while deleting..");
        $("#alertError").show();
    }
}




// CLIENT-MODEL================================================================
function validateResearchForm() {
   
    // researcher_id
    if ($("#researcher_id").val().trim() == "") {
        return "Insert Researcher ID!!!";
    }
   var tmpresid = $("#researcher_id").val().trim();
    if (!$.isNumeric(tmpresid)) {
        return "Insert a numerical value for Researcher ID.";
    }

    // research_name------------------------------
    if ($("#research_name").val().trim() == "") {
        return "Insert Research Name!!!";
    }

   // description------------------------------
    if ($("#description").val().trim() == "") {
        return "Insert Description!!!";
    }

  // category------------------------------
   if ($("#category").val().trim() == "-") {
        return "Insert Category!!!";
    }


   // expec budg------------------------------
    if ($("#expec_budg").val().trim() == "") {
        return "Insert Expected budget!!!";
    }
    // is numerical value
    var tmpBudget = $("#expec_budg").val().trim();
    if (!$.isNumeric(tmpBudget)) {
        return "Insert a numerical value for Expected Budget.";
    }
     $("#expec_budg").val(parseFloat(tmpBudget).toFixed(2));
  

    return true;
}