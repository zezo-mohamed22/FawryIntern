package ProductJX;

import Modules.storeManager;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Optional;

import Modules.Product;

import java.util.Set;

@Path("/product")
public class store {
    @Inject
    private storeManager manager;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response WelcomeToStore() {
        return Response.status(Response.Status.OK)
                .entity("{\"WelcomeToStore\": \"Frist one\"}").build();
    }

    @GET
    @Path("/getProduct/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProduct(@PathParam("name") String name) {
        Optional<Product> product = manager.searchByName(name);

        if (product.isEmpty())
            return Response.status(Response.Status.OK).entity("{\"Product \": \"Not founded\"}").build();

        return Response.status(Response.Status.OK).entity(product.get()).build();
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProducts() {
        Set<Product> products = manager.getProducts();

        return Response.status(Response.Status.OK).entity(products).build();

    }

    @GET
    @Path("/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteProduct(@PathParam("name") String name) {

        String message = manager.removeByName(name);
        if (!message.equals("Product is removed from the store")) {
            return Response.status(Response.Status.OK)
                    .entity("{\"message\": \"Product doesn't appear in store\"}")
                    .build();
        }

        return Response.status(Response.Status.OK)
                .entity("{\"message\": \"" + message + "\"}")
                .build();
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/AddProduct/{name}/{price}")

    public Response createProduct(@PathParam("name") String name, @PathParam("price") double price) {
        int id = manager.getProducts().size() + 1;
        Product product1 = new Product(id, name, price);

        String message = manager.add(product1);

        if (!message.equals("Product has been added successfully")) {
            return Response.status(Response.Status.OK).entity("{\"message\": \"" + message + "\"}")
                    .build();
        }
        return Response.status(Response.Status.OK).entity("{\"message\": \"" + message + "\"}")
                .build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateProduct(Product updatedProduct) {
        String message = manager.updateProduct(updatedProduct);
        if (!message.equals("Product has been updated successfully")) {
            return Response.status(Response.Status.OK).entity(message).build();
        }
        return Response.status(Response.Status.OK).entity(message).build();
    }
}
