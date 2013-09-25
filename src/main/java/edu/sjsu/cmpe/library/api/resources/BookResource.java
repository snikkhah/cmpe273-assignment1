package edu.sjsu.cmpe.library.api.resources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.jboss.logging.Param;



import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.yammer.metrics.annotation.Timed;

import edu.sjsu.cmpe.library.domain.Book;
import edu.sjsu.cmpe.library.domain.Review;
import edu.sjsu.cmpe.library.dto.AuthorDto;
import edu.sjsu.cmpe.library.dto.AuthorsDto;
import edu.sjsu.cmpe.library.dto.BookDto;
import edu.sjsu.cmpe.library.dto.LinkDto;
import edu.sjsu.cmpe.library.dto.LinksDto;
import edu.sjsu.cmpe.library.dto.ReviewDto;
import edu.sjsu.cmpe.library.dto.ReviewsDto;
import edu.sjsu.cmpe.library.domain.Author;

	@Path("/v1/books")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public class BookResource {
		private static HashMap<Long,Book> books=new HashMap<Long,Book>();
		private static Long isbn = new Long(1);
	//	private BookStorage books;
	    public BookResource() {
		// do nothing
	    }

    @GET
    @Path("/{isbn}")
    @Timed(name = "view-book")
    public BookDto getBookByIsbn(@PathParam("isbn") Long isbn) {
	// FIXME - Dummy code	
	Book book = new Book();
	book=books.get(isbn);
	BookDto bookResponse = new BookDto(book);
	bookResponse.addLink(new LinkDto("view-book", "/books/" + book.getIsbn(),
    		"GET"));
    	bookResponse.addLink(new LinkDto("update-book",
    		"/books/" + book.getIsbn(), "PUT"));
    	bookResponse.addLink(new LinkDto("delete-book",
        		"/books/" + book.getIsbn(), "DELETE"));
    	bookResponse.addLink(new LinkDto("create-review",
        		"/books/" + book.getIsbn() +"/reviews", "POST"));
    	if (book.getReviews().size()>0){
    	bookResponse.addLink(new LinkDto("view-all-reviews",
        		"/books/" + book.getIsbn() + "/reviews", "GET"));
    	}
	// add more links
	return bookResponse;
    }
    
    
    @POST
    @Timed(name = "create-book")
    public Response setBookByIsbn(@Param() String body) throws JsonParseException, IOException {
	// FIXME - Dummy code
    	Book book = new Book();
//		LinksDto authors = new LinksDto();
    	Author author = new Author();
		ArrayList<Author> authors = new ArrayList<Author>();
    	JsonFactory f = new JsonFactory();
    	JsonParser jp = f.createJsonParser(body);	
    	String fieldname;
    	String value;
    	JsonToken token;
    	token = jp.nextToken();
    	token = jp.nextToken();
    	while (token != JsonToken.END_OBJECT) {
    		
    			fieldname = jp.getCurrentName();
    			if ("title".equals(fieldname)){ 
    				token = jp.nextToken();
        			value = jp.getValueAsString();
    				book.setTitle(value);
    			}
    			else if ("publication-date".equals(fieldname)){  
    				token = jp.nextToken();
        			value = jp.getValueAsString();
    				book.setPublicationdDate(value);
    			}
    			else if ("language".equals(fieldname)){  
    				token = jp.nextToken();
        			value = jp.getValueAsString();
    				book.setLanguage(value);
    			}
    			else if ("num-pages".equals(fieldname)){  
    				token = jp.nextToken();
    				book.setNumPages(jp.getIntValue());
    			}
    			else if ("status".equals(fieldname)){  
    				token = jp.nextToken();
        			value = jp.getValueAsString();
    				book.setStatus(value);
    			}
    			else if ("authors".equals(fieldname)){  
    				token = jp.nextToken();
    				if (token == JsonToken.START_ARRAY){
    				token = jp.nextToken();
        			int count=1;
//    				token = jp.nextToken();
        			while (token != JsonToken.END_ARRAY){
        				fieldname = jp.getCurrentName();
        				author = new Author();
        				if ("name".equals(fieldname)){
        					token = jp.nextToken();
            				value = jp.getValueAsString();
            				author.setName(value);
            				author.setId(count);
            				authors.add(author);
//        					authors.addLink(new LinkDto("view-author", "/books/1/authors/"+count,"GET"));
            				count++;
            			}
        				token = jp.nextToken();
        			}
    				}
    				else {
    					author = new Author();
    					token = jp.nextToken();
    					fieldname = jp.getCurrentName();
    					if ("name".equals(fieldname)){
    					token = jp.nextToken();
            			value = jp.getValueAsString();
        				author.setName(value);
        				author.setId(1);
        				authors.add(author);
//            			authors.addLink(new LinkDto("view-author", "/books/1/authors/1","GET"));
    					}
    				}
    			}
    			token = jp.nextToken();
    			
    		}
    	
    	book.setIsbn(isbn);
    	isbn++;
    	book.setAuthors(authors);
    	books.put(book.getIsbn(), book);
    	
    	LinksDto links = new LinksDto();
    	links.addLink(new LinkDto("view-book", "/books/" + book.getIsbn(),
    		"GET"));
    	links.addLink(new LinkDto("update-book",
    		"/books/" + book.getIsbn(), "PUT"));
    	links.addLink(new LinkDto("update-book",
        		"/books/" + book.getIsbn(), "POST"));
    	links.addLink(new LinkDto("delete-book",
        		"/books/" + book.getIsbn(), "DELETE"));
    	links.addLink(new LinkDto("create-review",
        		"/books/" + book.getIsbn() +"/reviews", "POST"));
    	return Response.status(201).entity(links).build();
    }
   
    @DELETE
    @Path("/{isbn}")
    @Timed(name = "delete-book")
    public LinkDto deleteBookByIsbn(@PathParam("isbn") Long isbn) {
	// FIXME - Dummy code
    	books.remove(isbn);
    	return new LinkDto("create-book", "/books","POST");
    }

    @PUT
    @Path("/{isbn}")
    @Timed(name = "update-book")
    public LinksDto updateBookByIsbn(@PathParam("isbn") Long isbn,@QueryParam("status") String status) {
	// FIXME - Dummy code
    	books.get(isbn).setStatus(status);
    	LinksDto bookResponse = new LinksDto();
    	bookResponse.addLink(new LinkDto("view-book", "/books/" + isbn,
        		"GET"));
        	bookResponse.addLink(new LinkDto("update-book",
        		"/books/" + isbn, "PUT"));
        	bookResponse.addLink(new LinkDto("update-book",
            		"/books/" + isbn, "POST"));
        	bookResponse.addLink(new LinkDto("delete-book",
            		"/books/" + isbn, "DELETE"));
        	bookResponse.addLink(new LinkDto("create-review",
            		"/books/" + isbn +"/reviews", "POST"));
        	if (books.get(isbn).getReviews().size()>0){
            	bookResponse.addLink(new LinkDto("view-all-reviews",
                		"/books/" + isbn + "/reviews", "GET"));
            	}
    	return bookResponse;
  
}

    @POST
    @Path("/{isbn}/reviews")
    @Timed(name = "create-review")
    public Response createBookReviewByIsbn(@PathParam("isbn") Long isbn, @Param String body) throws JsonParseException, IOException {
	// FIXME - Dummy code
    	Review review =	new Review();
    	JsonFactory f = new JsonFactory();
    	JsonParser jp = f.createJsonParser(body);	
    	String fieldname;
    	String value;
    	int rate=0;
    	int reviewcount=0;
    	JsonToken token;
    	token = jp.nextToken();
    	token = jp.nextToken();
    	while (token != JsonToken.END_OBJECT) {
    		
    			fieldname = jp.getCurrentName();
    			if ("rating".equals(fieldname)){ 
    				token = jp.nextToken();
        			rate = jp.getIntValue();
    				review.setRating(rate);;
    			}
    			else if ("comment".equals(fieldname)){ 
    				token = jp.nextToken();
        			value = jp.getValueAsString();
    				review.setComment(value);
    			}
    			token = jp.nextToken();		
    	}

    	reviewcount = books.get(isbn).getReviews().size()+1;
    	review.setId(reviewcount);    	
    	books.get(isbn).getReviews().add(review);
    	LinkDto link = new LinkDto("view-review", "/books/" + isbn + "/reviews/ " + reviewcount,"GET");

    	return Response.status(201).entity(link).build();
    }
    @GET
    @Path("/{isbn}/reviews/{id}")
    @Timed(name = "view-review")
    public ReviewDto getReviewByIsbnById(@PathParam("isbn") Long isbn, @PathParam("id") int id) {
	// FIXME - Dummy code	
    	Review review = books.get(isbn).getReviews().get(id-1);
    	ReviewDto reviewResponse = new ReviewDto(review);
    	reviewResponse.addLink(new LinkDto("view-review", "/books/" + isbn + "/reviews/" + id,
    		"GET"));
	// add more links
	return reviewResponse;
    }
    
    @GET
    @Path("/{isbn}/reviews")
    @Timed(name = "view-all-review")
    public ReviewsDto getAllReviewByIsbn(@PathParam("isbn") Long isbn) {
	// FIXME - Dummy code	
    	ArrayList<Review> reviews = books.get(isbn).getReviews();
    	ReviewsDto reviewsResponse = new ReviewsDto(reviews);
    	reviewsResponse.addLink(null);
	// add more links
	return reviewsResponse;
    }
    
    @GET
    @Path("/{isbn}/authors/{id}")
    @Timed(name = "view-author")
    public AuthorDto getAuthorByIsbnById(@PathParam("isbn") Long isbn, @PathParam("id") int id) {
	// FIXME - Dummy code	
    	Author author = books.get(isbn).getAuthorsArray().get(id-1);
    	AuthorDto authorResponse = new AuthorDto(author);
    	authorResponse.addLink(new LinkDto("view-author", "/books/" + isbn + "/author/" + id,
    		"GET"));
	// add more links
	return authorResponse;
    }
    @GET
    @Path("/{isbn}/authors")
    @Timed(name = "view-all-authors")
    public AuthorsDto getAllAuthorsByIsbn(@PathParam("isbn") Long isbn) {
	// FIXME - Dummy code	
    	ArrayList<Author> authors = books.get(isbn).getAuthorsArray();
    	AuthorsDto authorsResponse = new AuthorsDto(authors);
    	authorsResponse.addLink(null);
	// add more links
    	return authorsResponse;
    }
  
}
