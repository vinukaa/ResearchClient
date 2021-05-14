<%@ page import="com.researchclient.Research"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Research Form</title>
<script src="Components/jquery-3.2.1.min.js"></script>
<script src="Components/research.js"></script>
<link rel="stylesheet" href="Views/bootstrap.min.css">
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col">



				<div class="container pt-5 pb-5">

					<center>
						<u><h4 class="mb-3 pb-3">Research Form</h4></u>
					</center>
					<form class="needs-validation" novalidate id="researchform">
					<input type='hidden' id='hiddenResearchIDSave' name='hiddenResearchIDSave' value=''>
						<div class="row">
						
							<div class="col-md-12 mb-3">
								<label for="researcher_id">Researcher ID:</label> <input
									type="text" class="form-control" id="researcher_id" name="researcher_id">

							</div>
						</div>

						<div class="mb-3">
							<label for="research_name">Research Name:</label> <input
								type="text" class="form-control" id="research_name" name="research_name">


						</div>

						<div class="mb-3">
							<label for="description">Description:</label>
							<textarea class="form-control" id="description" name="description"></textarea>
						</div>
						
						
						<div class="row">
							<div class="col-md-5 mb-3">
								<label for="category">Category:</label> <select
									class="custom-select d-block w-100" id="category" name="category">
									<option>-</option>
									<option >ICT</option>
									<option>Finance</option>
									<option>Management</option>
								</select>

							</div>



							<div class="col-md-7 mb-3">
								<label for="expec_budg">Expected Budget:</label> <input
									type="text" class="form-control" id="expec_budg" name="expec_budg">
							</div>
						</div>



						<button class="btn btn-primary btn-lg btn-block" type="button"
							id="addresearch">SAVE RESEARCH</button>
					</form>
				</div>
			</div>

			<br>
			<br> <br>

			

			<br>
			<br>
			<br>
			

		</div>
		
		<div id='alertSuccess' name='alertSuccess'
				class='alert alert-success'></div>
			<div id='alertError' name='alertError' class='alert alert-danger'></div>
			<br>
			
			
			
		<div id="divResearchGrid">
				<%
				Research researchObject = new Research();
				out.print(researchObject.getResearch());
				%>
			</div>
	</div>
	
</body>
</html>