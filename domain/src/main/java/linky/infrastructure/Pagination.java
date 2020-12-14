package linky.infrastructure;

public class Pagination {
 
   private final int page;
   private final int size;

   public Pagination(final int page, final int size) {
      if (page < 0) {
         throw new IllegalArgumentException("");
      }
      if (size <= 0) {
         throw new IllegalArgumentException("");
      }
      this.page = page;
      this.size = size;
   }

   public int page() {
      return page;
   }

   public int size() {
      return size;
   }

}
