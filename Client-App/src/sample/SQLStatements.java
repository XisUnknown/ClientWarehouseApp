package sample;

public class SQLStatements {
    private String createChoiceBoxStmt = "SELECT productID,productname FROM product WHERE instock <> 0";
    private String createAllProductsTableStmt = "SELECT productID,productname,singleprice,bulkprice,instock FROM product WHERE instock <> 0";
    private String createCoffeeTableStmt = "SELECT productID,productname,singleprice,bulkprice,instock FROM product WHERE category LIKE ('Coffee') AND instock <> 0";

}
