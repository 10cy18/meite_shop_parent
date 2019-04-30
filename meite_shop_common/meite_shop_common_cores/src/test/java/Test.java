/**
 * @Auther: cy
 * @Date: 2019/4/30 14:22
 * @Description:
 */
public class Test {

    public static void main(String[] args) {
        String registrationCodeMessage = "哈哈哈%ss哈哈哈";
        String content = String.format(registrationCodeMessage, 123);
        System.out.println(content);
    }
}
