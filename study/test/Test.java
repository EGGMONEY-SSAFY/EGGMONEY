import java.util.*;
import java.util.logging.Logger;

public class Test {

  public static void main(String[] args) {
    Member member = new Member();
    member.setAge(20);
    member.setName("싸피");
    System.out.println(member.getAge());
    System.out.println(member.getName());
  }
}
