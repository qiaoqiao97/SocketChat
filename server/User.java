
public class User {
    private int id;
    private MyChannel channel;

    public User() {
    }

    public User(int id, MyChannel channel) {
        this.id = id;
        this.channel = channel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MyChannel getChannel() {
        return channel;
    }

    public void setChannel(MyChannel channel) {
        this.channel = channel;
    }
}
