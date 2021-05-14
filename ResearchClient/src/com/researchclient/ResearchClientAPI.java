package com.researchclient;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

/**
 * Servlet implementation class ResearchClientAPI
 */
@WebServlet("/ResearchClientAPI")
public class ResearchClientAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 Research research = new Research();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ResearchClientAPI() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JsonObject output = research.insertResearch(
				Integer.parseInt(request.getParameter("researcher_id")),
				request.getParameter("research_name"),
				request.getParameter("description"),
				request.getParameter("category"),
				Double.parseDouble(request.getParameter("expec_budg")));
		response.getWriter().write(output.toString());
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, String> paras = getParasMap(request);
		JsonObject output = research.updateResearch(
				Integer.parseInt(paras.get("hiddenResearchIDSave").toString()),
				Integer.parseInt(paras.get("researcher_id")),
				paras.get("research_name"),
				paras.get("description"),
				paras.get("category"),
				Double.parseDouble(paras.get("expec_budg")));
		response.getWriter().write(output.toString());
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, String> paras = getParasMap(request);
	
		JsonObject output = research.deleteResearch(Integer.parseInt(paras.get("researchID").toString()));
		response.getWriter().write(output.toString());
	}
	
	
	// Convert request parameters to a Map
		private static Map<String,String> getParasMap(HttpServletRequest request)
		{
			Map<String, String> map = new HashMap<String, String>();
			try
			{
				Scanner scanner = new Scanner(request.getInputStream(), "UTF-8");
				String queryString = scanner.hasNext() ?
						scanner.useDelimiter("\\A").next() : "";
				scanner.close();
				String[] params = queryString.split("&");
				for (String param : params)
				{
					String[] p = param.split("=");
					map.put(p[0], java.net.URLDecoder.decode(p[1], StandardCharsets.UTF_8.name()));
				}
			}
			catch (Exception e)
			{
			}
			return map;
		}

}
