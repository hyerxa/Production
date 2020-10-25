public class MoviePlayer extends Product implements MultimediaControl{
    Screen screen;
    MonitorType monitorType;

    MoviePlayer(String name, String manufacturer, Screen screen, MonitorType monitorType) {
        this.name = name;
        this.manufacturer = manufacturer;
        this.screen = screen;
        this.monitorType = monitorType;
        this.type = "VISUAL";
    }

    public void play() {
        System.out.println("Playing movie");
    }

    public void stop() {
        System.out.println("Stopping movie");
    }

    public void previous() {
        System.out.println("Previous movie");
    }

    public void next() {
        System.out.println("Next movie");
    }

    public String toString() {
        return super.toString() + "\n" + "Monitor Type:" + monitorType + "\n" + screen;
    }
}
