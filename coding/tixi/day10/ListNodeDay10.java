package coding.tixi.day10;

/**
 * @author linzherong
 * @date 2025/3/30 11:14
 */
public class ListNodeDay10 {

    public ListNodeDay10 next;
    public ListNodeDay10 rand;

    public ListNodeDay10() {}
    public ListNodeDay10(ListNodeDay10 next, ListNodeDay10 rand) {
        this.next = next;
        this.rand = rand;
    }

    public ListNodeDay10 getNext() {
        return next;
    }

    public void setNext(ListNodeDay10 next) {
        this.next = next;
    }

    public ListNodeDay10 getRand() {
        return rand;
    }

    public void setRand(ListNodeDay10 rand) {
        this.rand = rand;
    }
}
