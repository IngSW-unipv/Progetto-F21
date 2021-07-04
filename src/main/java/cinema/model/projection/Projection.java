package cinema.model.projection;

import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;

import cinema.model.cinema.util.RoomException;
import cinema.model.projection.util.ProjectionException;
import cinema.model.Movie;
import cinema.model.cinema.PhysicalSeat;
import cinema.model.cinema.Room;


/** BREVE DESCRIZIONE CLASSE Projection
 * 
 * @author Screaming Hairy Armadillo Team
 *
 * Questa classe comprende tutte le informazioni e metodi
 * che servono per rappresentare una proiezione che viene
 * effettuata dal cinema.
 */
public class Projection implements Comparable<Projection> {
	
	
	/** ATTRIBUTI
	 * @param id			Id
	 * @param movie			Film associato
	 * @param room			Sala in cui il film verrà proiettato
	 * @param dateTime		Data e ora
	 * @param price			Prezzo
	 * @param seats			Posti della sala in cui il film è proiettato
	 */
	private int id;
	private Movie movie;
	private Room room;
	private LocalDateTime dateTime;
	private double price;
	private ArrayList<ArrayList<ProjectionSeat>> seats;

	/**
	 * COSTRUTTORE 
	 * @param id
	 * @param movie
	 * @param dateTime
	 * @param price
	 * @param room
	 */
	public Projection(int id, Movie movie, LocalDateTime dateTime, double price, Room room) {
		this.id = id;
		this.movie = movie;
		this.dateTime = dateTime;
		this.price = Math.round(price * 100.0)/100.0;
		this.room = room;
		this.seats = new ArrayList<ArrayList<ProjectionSeat>>();
		for(int i = 0; i < room.getNumberOfRows(); i++) {
			ArrayList<ProjectionSeat> row = new ArrayList<ProjectionSeat>();
			for(int j = 0; j < room.getNumberOfCols(); j++) {
				row.add(new ProjectionSeat(room.getSeat(i, j), true));
			}
			seats.add(row);
		}
	}

	
	/** COSTRUTTORE DI DEFAULT */
	public Projection() {
		this.seats = new ArrayList<ArrayList<ProjectionSeat>>();
	}

	
	/**	METODO per impostare l'id di una proiezione
	 * @param id
	 * @throws InvalidProjectionIdException
	 */
	public void setId(int id) throws ProjectionException {
		if (id < 0)
			throw new ProjectionException("L'ID della proiezione deve essere non negativo");
		this.id = id;
	}

	
	/** METODO per associare un film alla proiezione */
	public void setMovie(Movie movie) {
		this.movie = movie;
	}


	/** METODO per aggiungere la sala in cui è proiettato il film*/
	public void setRoom(Room room) {
		this.room = room;
		if (seats.size() != 0) {
			seats.removeAll(seats);
		}
		for(int i = 0; i < room.getNumberOfRows(); i++) {
			ArrayList<ProjectionSeat> row = new ArrayList<ProjectionSeat>();
			for(int j = 0; j < room.getNumberOfCols(); j++) {
				row.add(new ProjectionSeat(room.getSeat(i, j), true));
			}
			seats.add(row);
		}
	}


	/** METODO per aggiungere la data della proiezione
	 * @param dateTime
	 * @throws InvalidProjectionDateTimeException
	 */
	public void setDateTime(LocalDateTime dateTime) throws ProjectionException {
		if (dateTime.isBefore(LocalDateTime.now()))
			throw new ProjectionException("La data della proiezione non può essere nel passato.");
		this.dateTime = dateTime;
	}


	/** METODO per aggiungere il prezzo alla proiezione
	 * 
	 * @param price
	 * @throws InvalidPriceException
	 */
	public void setPrice(double price) throws ProjectionException {
		if (price <= 0)
			throw new ProjectionException("Il prezzo del biglietto per una proiezione non può essere negativo.");
		this.price = Math.round(price * 100.0)/100.0;
	}


	/**
	 * METODO che serve per verificare se un posto specifico
	 * è libero.
	 * @param row, col		Coordinate 
	 * @return				True: libero, False: occupato
	 * @throws InvalidRoomSeatCoordinatesException 
	 */
	public boolean checkIfSeatIsAvailable(int row, int col) throws RoomException {
		try {
			return seats.get(row).get(col).isAvailable();
		}catch (IndexOutOfBoundsException e) {
			throw new RoomException("Il posto selezionato (" + Room.rowIndexToRowLetter(row) +"-"+ (col + 1) +" non esiste.");
		}
	}
	
	
	/**
	 * METODO per farsi restituire il numero di posti liberi per quella stanza
	 * @return int  Numero di posti disponibili/liberi
	 * @throws InvalidRoomSeatCoordinatesException 
	 */
	public int getNumberAvailableSeat() throws RoomException {
		int availableSeats = 0;
		for (int i = 0 ; i < this.getRoom().getNumberOfRows() ; i++) {
			for (int j = 0 ; j < this.getRoom().getNumberOfCols() ; j++) {
				if (checkIfSeatIsAvailable(i, j)) {
					availableSeats++;
				}
			}
		}
		return availableSeats;
	}
	
	
	/**
	 * METODO occupa posto della sala in cui è fatta la proiezione
	 * @param row, col		Coordinate 
	 * @return esito 		Esito occupazione del posto
	 * @throws InvalidRoomSeatCoordinatesException 
	 */
	public boolean takeSeat(int row, int col) throws RoomException {
			if (checkIfSeatIsAvailable(row, col)) {
				seats.get(row).get(col).setAvailable(false);
				return true;
			}
			return false;
	}
	
	
	/**
	 * METODO per liberare il posto di una sala
	 * @param row, col		Coordinate 
	 * @return esito 		Esito rilascio del posto
	 * @throws InvalidRoomSeatCoordinatesException 
	 */
	public boolean freeSeat(int row, int col) throws RoomException {
		if(!checkIfSeatIsAvailable(row, col)) {
			seats.get(row).get(col).setAvailable(true);
			return true;
		}
		return false;
	}
	
	
	/**
	 * METODO per restituire un posto, date le coordinate
	 * @param row, col		Coordinate 
	 * @return 		 		Posto fisico
	 * @throws InvalidRoomSeatCoordinatesException 
	 */
	public PhysicalSeat getPhysicalSeat(int row, int col) throws RoomException {
		 return this.getSeats().get(row).get(col).getPhysicalSeat();
	}

	
	/**
	 * METODO per farsi dare le coordinate di un posto
	 * @param s			Posto fisico
	 * @return			Coordinate
	 * @throws InvalidRoomSeatCoordinatesException 
	 */
	public String getSeatCoordinates(PhysicalSeat s) throws RoomException {
		for(int i=0; i < room.getNumberOfRows(); i++) {
			for(int j=0; j < room.getNumberOfCols(); j++) {
				if(getPhysicalSeat(i,j) == s)
					return Room.rowIndexToRowLetter(i) + (j+1);		
			}
		}
		return null;		
	}
	
	
	@Override
	public int compareTo(Projection p) {
		return this.dateTime.compareTo(p.getDateTime());
	}
	
	
	/**
	 * METODO per stampare le caratteristiche principali della classe
	 * @return caratteristiche
	 */
	@Override 
	public String toString() {
		int availableSeats = 0;
		try { 
			availableSeats = this.getNumberAvailableSeat();
		}
		catch (RoomException e) {
		}
		
		String dayOfWeek = getDateTime().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ITALIAN);
		String month = getDateTime().getMonth().getDisplayName(TextStyle.FULL, Locale.ITALIAN);
		
		return "Sala n°: " + this.getRoom().getNumber() + "\n"
				+ "Data: " + dayOfWeek.toUpperCase().charAt(0) + dayOfWeek.substring(1)
				+ " " + this.getDateTime().getDayOfMonth()
				+ " " + month.toUpperCase().charAt(0) + month.substring(1)
				+ " " + this.getDateTime().getYear() + "   "
				+ "Ora: " + String.format("%02d", this.getDateTime().getHour()) 
				+ ":" + String.format("%02d", this.getDateTime().getMinute()) + "\n"
				+ "Prezzo: " + String.format("%.2f €", this.getPrice()) + "\n" 
				+ "Posti disponibili: " + availableSeats + "\n";
	}


	/** METODO per farsi dire il prezzo della proiezione */
	public double getPrice() {
		return price;
	}

	
	/** METODO per farsi dire la data e l'ora della proiezione */
	public LocalDateTime getDateTime() {
		return dateTime;
	}

	
	/** METODO per farsi dire la sala della proiezione */
	public Room getRoom() {
		return room;
	}

	
	/** METODO per farsi dire l'id proiezione */
	public int getId() {
		return id;
	}
	
	
	/** METODO per farsi dire i posti della proiezione */
	public ArrayList<ArrayList<ProjectionSeat>> getSeats() {
		return this.seats;
	}


	/** METODO per farsi dire il film della proiezione */
	public Movie getMovie() {
		return movie;
	}
}