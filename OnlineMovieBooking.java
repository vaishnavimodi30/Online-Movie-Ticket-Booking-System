/*
1. Online Movie Ticket Booking System

   - Description: Multiple users attempt to book seats for a movie show.
     The system ensures no two users can book the same seat.
   - Input: User ID, number of seats requested, and seat numbers.
   - Output: Confirmation message or "Seat already booked. Try again!"
*/

class MovieBooking {
    private String[] bookedSeats = new String[20];
    private int count = 0;

    synchronized void bookSeats(String userId, String[] seats) {
        String booked = "";
        String already = "";

        for (int i = 0; i < seats.length; i++) {
            boolean found = false;

            for (int j = 0; j < count; j++) {
                if (bookedSeats[j].equals(seats[i])) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                bookedSeats[count++] = seats[i];
                booked += seats[i] + " ";
            } else {
                already += seats[i] + " ";
            }
        }

        if (!booked.isEmpty() && already.isEmpty()) {
            System.out.println(userId + ":  Successfully booked seats: " + booked);
        } else if (!booked.isEmpty() && !already.isEmpty()) {
            System.out.println(userId + ": Some seats were already booked (" + already + ")Booked only: " + booked);
        } else {
            System.out.println(userId + ": All seats already booked (" + already + "). Please try again!");
        }
    }

    synchronized void viewBookedSeats(String userId) {
        System.out.print(userId + ": Currently booked seats: ");
        if (count == 0) {
            System.out.println("No seats booked yet!");
        } else {
            for (int i = 0; i < count; i++) {
                System.out.print(bookedSeats[i] + " ");
            }
            System.out.println();
        }
    }
    synchronized void cancelSeats(String userId, String[] seats) {
        boolean anyCancelled = false;
        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < count; j++) {
                if (bookedSeats[j].equals(seats[i])) {
                    for (int k = j; k < count - 1; k++) {
                        bookedSeats[k] = bookedSeats[k + 1];
                    }
                    count--;
                    anyCancelled = true;
                    System.out.println(userId + ": Cancelled seat: " + seats[i]);
                    break;
                }
            }
        }
        if (!anyCancelled) {
            System.out.println(userId + ": No matching seats found to cancel!");
        }
    }
}
class UserThread extends Thread {
    MovieBooking booking;
    String userId;
    String[] seats;
    int choice;

    UserThread(MovieBooking booking, String userId, String[] seats, int choice) {
        this.booking = booking;
        this.userId = userId;
        this.seats = seats;
        this.choice = choice;
    }

    public void run() {
        switch (choice) {
            case 1:
                booking.bookSeats(userId, seats);
                break;
            case 2:
                booking.viewBookedSeats(userId);
                break;
            case 3:
                booking.cancelSeats(userId, seats);
                break;
            default:
                System.out.println(userId + ": Invalid choice!");
        }
    }
}
public class OnlineMovieBooking {
    public static void main(String[] args) {
        MovieBooking booking = new MovieBooking();

        String[] user1Seats = {"A1", "A2"};
        String[] user2Seats = {"A2", "A3"};
        String[] user3Seats = {"A1"};

        UserThread user1 = new UserThread(booking, "Kavya", user1Seats, 1); 
        UserThread user2 = new UserThread(booking, "Soham", user2Seats, 1);
        UserThread user3 = new UserThread(booking, "Riya", user3Seats, 3); 

        user1.start();
        user2.start();
        user3.start();
    }
}
