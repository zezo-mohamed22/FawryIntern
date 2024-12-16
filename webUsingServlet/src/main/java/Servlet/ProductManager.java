package Servlet;

import Modules.Product;
import Modules.storeManager;

import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;
import java.util.Set;

@WebServlet("/ProductManager")
public class ProductManager extends HttpServlet {
    private static storeManager manager;

    public ProductManager() {
        super();
    }

    @Override
    public void init() throws ServletException {
        super.init();
        if (manager == null) {
            manager = new storeManager();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "all";
        }

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        switch(action) {
            case "all":
                listProducts(out);
                break;
            case "add":
                addProduct(request, out);
                break;
            case "remove":
                removeProduct(request, out);
                break;
            case "update":
                updateProduct(request, out);
                break;
            case "searchByName":
                searchByName(request, out);
                break;
            case "searchById":
                searchById(request, out);
                break;
            default:
                out.println("<h2>Unknown action: " + action + "</h2>");
                break;
        }

    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            sendResponse(response, "Action parameter is missing.");
            return;
        }

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        switch (action) {
            case "add":
                addProduct(request, out);
                break;
            case "update":
                updateProduct(request, out);
                break;
            case "remove":
                removeProduct(request, out);
                break;
            default:
                out.println("<h2>Unknown action: " + action + "</h2>");
                break;
        }
    }

    private void listProducts(PrintWriter out) {
        Set<Product> products = manager.getProducts();
        out.println("<h1>Product List</h1>");
        if (products.isEmpty()) {
            out.println("<p>No products available.</p>");
            return;
        }
        out.println("<ul>");
        for (Product p : products) {
            out.println("<li>" + p.toString() + "</li>");
        }
        out.println("</ul>");
    }


    private void searchByName(HttpServletRequest request, PrintWriter out) {
        String name = request.getParameter("name");
        if (name == null || name.trim().isEmpty()) {
            out.println("<h2>Name parameter is missing or empty.</h2>");
            return;
        }
        Optional<Product> product = manager.searchByName(name);
        if (product.isPresent()) {
            out.println("<h2>Product Found:</h2>");
            out.println("<p>" + product.get().toString() + "</p>");
        } else {
            out.println("<h2>No product found with name: " + name + "</h2>");
        }
    }

    private void searchById(HttpServletRequest request, PrintWriter out) {
        String idStr = request.getParameter("id");
        if (idStr == null || idStr.trim().isEmpty()) {
            out.println("<h2>ID parameter is missing or empty.</h2>");
            return;
        }
        try {
            int id = Integer.parseInt(idStr);
            Optional<Product> product = manager.searchById(id);
            if (product.isPresent()) {
                out.println("<h2>Product Found:</h2>");
                out.println("<p>" + product.get().toString() + "</p>");
            } else {
                out.println("<h2>No product found with ID: " + id + "</h2>");
            }
        } catch (NumberFormatException e) {
            out.println("<h2>Invalid ID format. ID must be an integer.</h2>");
        }
    }


    private void addProduct(HttpServletRequest request, PrintWriter out) {
        String idStr = request.getParameter("id");
        String name = request.getParameter("name");
        String priceStr = request.getParameter("price");

        if (idStr == null || name == null || priceStr == null ||
                idStr.trim().isEmpty() || name.trim().isEmpty() || priceStr.trim().isEmpty()) {
            out.println("<h2>All parameters (id, name, price) are required.</h2>");
            return;
        }

        try {
            int id = Integer.parseInt(idStr);
            double price = Double.parseDouble(priceStr);
            Product product = new Product(id, name, price);
            String result = manager.add(product);
            out.println("<h2>" + result + "</h2>");
        } catch (NumberFormatException e) {
            out.println("<h2>Invalid number format for ID or Price.</h2>");
        }
    }
    private void updateProduct(HttpServletRequest request, PrintWriter out) {
        String idStr = request.getParameter("id");
        String name = request.getParameter("name");
        String priceStr = request.getParameter("price");

        if (idStr == null || name == null || priceStr == null ||
                idStr.trim().isEmpty() || name.trim().isEmpty() || priceStr.trim().isEmpty()) {
            out.println("<h2>All parameters (id, name, price) are required.</h2>");
            return;
        }

        try {
            int id = Integer.parseInt(idStr);
            double price = Double.parseDouble(priceStr);
            Product product = new Product(id, name, price);
            String result = manager.updateProduct(product);
            out.println("<h2>" + result + "</h2>");
        } catch (NumberFormatException e) {
            out.println("<h2>Invalid number format for ID or Price.</h2>");
        }
    }


    private void removeProduct(HttpServletRequest request, PrintWriter out) {
        String name = request.getParameter("name");
        if (name == null || name.trim().isEmpty()) {
            out.println("<h2>Name parameter is missing or empty.</h2>");
            return;
        }
        String result = manager.removeByName(name);
        out.println("<h2>" + result + "</h2>");
    }
    private void sendResponse(HttpServletResponse response, String message) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<h2>" + message + "</h2>");
    }
}
