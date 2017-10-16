package risponditore;

/*java.util*/
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.function.Function;
import java.util.Scanner;

/*java.time*/
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

/*java.io*/
import java.io.IOException;
import java.io.BufferedReader;
import java.io.PrintWriter;

class Reception
{
    private final static String[][] pizzaList={
        {
            "Margherita", "Marinara", "Capricciosa", "Quattro formaggi", "Porsciutto e funghi", "Diavola", "Mari e monti", "Verdure"
        },
        {
            "4.5", "4.0", "6.5", "5.0", "5.5", "5.0", "7.0", "6.0"
        }
    };

    private final static String[][] drinksList={
        {
            "Acqua naturale", "Acqua frizzante", "Coca cola", "Fanta", "Sprite", "Chinotto", "Birra", "Vino bianco", "Vino rosso"
        },
        {
            "1.0", "1.0", "3.5", "3.5", "3.5", "3.5", "4.5", "5.0", "5.0"
        }
    };

    private final static String[][] hotelOptionsList={
        {
            "Camera singola", "Camera doppia", "Camera tripla", "Camera quadrupla"
        },
        {
            "69.0", "120.0", "180.0", "250.0"
        }
    };

    private final static String[][] appetizers={
        {
            "Crostini al patè di olive", "Prosciutto e melone", "Carpaccio di pesce spada"
        },
        {
            "5.00", "5.00", "6.50"
        }
    };

    private final static String[][] firstCourses={
        {
            "Pasta al pomodoro", "Lasagne alla bolognese", "Spaghetti allo scoglio", "Risotto con salsiccia e prosecco", "Pennette rucola e grana"
        },
        {
            "8.00", "9.00", "11.00", "12.00", "10.00"
        }
    };

    private final static String[][] secondCourses={
        {
            "Filetti di orata con capperi e olive", "Fritto misto pesce con verdure", "Arista di maiale al forno", "Filetto alla brace", "Insalata mista"
        },
        {
            "14.00", "16.00", "14.00", "21.00", "7.00"
        }
    };

    private final static String[][] desserts={
        {
            "Sorbetto al limone", "Panna cotta", "Tiramisù", "Cheesecake ai frutti di bosco"
        },
        {
            "3.00", "4.00", "4.50", "5.00"
        }
    };

    private final static String[][] apartmentsList={
        {
            "Monolocale divano letto", "Bilocale due posti letto", "Bilocale tre posti letto", "Bilocale quattro posti letto"
        },
        {
            "280.0", "350.0", "400.0", "450.0"
        }
    };

    private int clientNumber;
    private BufferedReader in;
    private PrintWriter out;

    private LinkedHashMap<String, Function<Integer, LinkedList<String>>> operations;

    Reception(int clientNumber, BufferedReader in, PrintWriter out)
    {
        this.clientNumber=clientNumber;
        this.in=in;
        this.out=out;

        operations=new LinkedHashMap<>();

        Function<Integer, LinkedList<String>> pizzeria=(Integer integer) ->
        {
            LinkedList<String> r=new LinkedList<>();
            double bill=0;
            System.out.println("Client #"+clientNumber+" has choosen pizzeria");
            r.add("Pizzeria: ");

            int n;
            while(true)
            {
                out.println("receive");
                out.println("Quante pizze vuole?");
                try
                {
                    out.println("send");
                    n=Integer.parseInt(in.readLine());
                    break;
                }
                catch(NumberFormatException e)
                {
                    out.println("receive");
                    out.println("Deve inserire un numero");
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
            }
            r.add("Quantità pizze: "+n);
            int l=pizzaList[0].length;
            for(int i=0; i<n; i++)
            {
                // r.add("Pizza #"+(i+1)+": ");
                out.println("receive");
                out.println("Che pizza vuole (#"+(i+1)+")\n");
                for(int j=0; j<l; j++)
                {
                    out.println("receive");
                    out.println(String.format("%d) %-25s\t(€%-4s)", j, pizzaList[0][j], pizzaList[1][j]));
                }
                while(true)
                {
                    try
                    {
                        out.println("send");
                        int decision=Integer.parseInt(in.readLine());

                        // r.add(pizzaList[0][decision]);
                        r.add(String.format("\tPizza #%d: %-15s\t(€%-4s)", i+1, pizzaList[0][decision], pizzaList[1][decision]));
                        bill+=Double.parseDouble(pizzaList[1][decision]);
                        break;
                    }
                    catch(IOException e)
                    {
                        e.printStackTrace();
                    }
                    catch(NumberFormatException e)
                    {
                        out.println("receive");
                        out.println("Deve inserire un numero corrispondente alla pizza desiderata");
                    }
                    catch(IndexOutOfBoundsException e)
                    {
                        out.println("receive");
                        out.println("Il numero che ha inserito non corrisponde a nessuna opzione");
                    }
                }
            }
            boolean exit=false;
            while(!exit)
            {
                out.println("receive");
                out.println("Vuole delle bibite?\t(s/n)");
                out.println("send");
                try
                {
                    switch(in.readLine().toLowerCase())
                    {
                        case "n": exit=true; break;
                        case "s": exit=true; bill+=addDrinks(r); break;
                        default: out.println("receive"); out.println("Non ho capito");
                    }
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
            }

            out.println("receive");
            out.println("Per che ora vuole ritirarle? Accettiamo prenotazioni fino alle 23 odierne  (hh:mm)");
            LocalTime now=LocalTime.now(), limit=LocalTime.of(23,0), withdrawal;
            while(true)
            {
                try
                {
                    out.println("send");
                    withdrawal=LocalTime.parse(in.readLine());
                    if(withdrawal.isAfter(now) && withdrawal.isBefore(limit))
                        break;
                    out.println("receive");
                    out.println("L\'ora che ha inserito non è valida. Accettiamo prenotazioni fino alle 23, e successivamente ad ora");

                }
                catch(DateTimeParseException e)
                {
                    out.println("receive");
                    out.println("L'ora che ha inserito non ha formato valido. Dev'essere hh:mm");
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
            }
            r.add("Orario di ritiro: "+withdrawal.toString());

            r.add("");
            r.add("Totale: € "+bill);

            return r;
        };

        Function<Integer, LinkedList<String>> hotel=(Integer integer) ->
        {
            System.out.println("Client #"+clientNumber+" has choosen hotel");
            LinkedList<String> r=new LinkedList<>();
            double bill=0;

            r.add("Hotel: ");

            LocalDate arrival, departure, arrivalLimit=LocalDate.of(LocalDate.now().plusYears(1).getYear(), 12, 1), departureLimit=LocalDate.of(LocalDate.now().plusYears(1).getYear(), 12, 31);
            while(true)
            {
                out.println("receive");
                out.println("Data di arrivo? (aaaa-mm-gg) Accettiamo arrivi fino al "+arrivalLimit.toString()+" escluso");
                out.println("send");
                try
                {
                    arrival=LocalDate.parse(in.readLine());
                    if(arrival.isAfter(LocalDate.now()) && arrival.isBefore(arrivalLimit))
                        break;
                    out.println("receive");
                    out.println("La data non è valida: non può essere nel passato, nè oltre il limite");
                    Thread.sleep(1000);
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();
                }
                catch(DateTimeParseException e)
                {
                    out.println("receive");
                    out.println("La data deve avere formato aaaa-mm-gg (mese e giorno devono includere lo 0 se inferiori a 10)");
                    try
                    {
                        Thread.sleep(2000);
                    }
                    catch(InterruptedException ex)
                    {
                        ex.printStackTrace();
                    }
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
            }
            System.out.println("Client #"+clientNumber+" has choosen "+arrival.toString()+" as arrival date");

            while(true)
            {
                out.println("receive");
                out.println("Data di partenza? (aaaa-mm-gg) Accettiamo partenze fino al "+departureLimit.toString()+" escluso");
                out.println("send");
                try
                {
                    departure=LocalDate.parse(in.readLine());
                    if(departure.isAfter(arrival) && departure.isBefore(departureLimit))
                        break;
                    out.println("receive");
                    out.println("La data non è valida: non può precedere quella di arrivo nè superare quella limite");
                    Thread.sleep(1500);
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();
                }
                catch(DateTimeParseException e)
                {
                    out.println("receive");
                    out.println("La data deve avere formato aaaa-mm-gg (mese e giorno devono includere lo 0 se inferiori a 10)");
                    try
                    {
                        Thread.sleep(2000);
                    }
                    catch(InterruptedException ex)
                    {
                        ex.printStackTrace();
                    }
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
            }
            System.out.println("Client #"+clientNumber+" has choosen "+departure.toString()+" as departure date");

            r.add("Data di arrivo: "+arrival.toString());
            r.add("Data di partenza: "+departure.toString());
            int days=(int) ChronoUnit.DAYS.between(arrival, departure);

            out.println("receive");
            out.println("scelga il tipo di soggiorno (i prezzi esposti si riferiscono alla tariffa per notte, con mezza pensione inclusa)");
            for(int i=0; i<hotelOptionsList[0].length; i++)
            {
                out.println("receive");
                out.println(String.format("%d) %-25s\t(€%-4s)", i, hotelOptionsList[0][i], hotelOptionsList[1][i]));
            }
            while(true)
            {
                try
                {
                    out.println("send");
                    int decision=Integer.parseInt(in.readLine());
                    bill+=Double.parseDouble(hotelOptionsList[1][decision])*days;
                    r.add("Tipo di prenotazione scelta: "+hotelOptionsList[0][decision]+"\t (€"+hotelOptionsList[1][decision]+"/notte)");
                    break;
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
                catch(NumberFormatException e)
                {
                    out.println("receive");
                    out.println("Deve inserire un numero che corrisponda all\'opzione desiderata");
                }
                catch(IndexOutOfBoundsException e)
                {
                    out.println("receive");

                    out.println("Il numero che ha selezionato è al di fuori dell'intervallo delle opzioni disponibili, scelga nuovamente");
                }
            }
            r.add("TOTALE: €"+String.valueOf(bill));

            return r;
        };

        Function<Integer, LinkedList<String>> restaurant=(Integer integer) ->
        {
            System.out.println("Client #"+clientNumber+" has choosen restaurant");

            LinkedList<String> r=new LinkedList<>();
            double bill=0;

            r.add("Ristorante:");

            out.println("receive");
            out.println("Per quante persone vuole prenotare?");
            int people;
            while(true)
            {
                try
                {
                    out.println("send");
                    people=Integer.parseInt(in.readLine());
                    break;
                }
                catch(NumberFormatException e)
                {
                    out.println("receive");
                    out.println("Deve inserire un numero");
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
            }
            r.add("Numero persone: "+people);

            for(int i=0; i<people; i++)
            {
                out.println("receive");
                out.println("Menu persona #"+(i+1));
                r.add("Menu persona #"+(i+1));
                r.add("\tAntipasto: ");
                out.println("receive");
                out.println("\tAntipasti: ");
                for(int j=0; j<appetizers[0].length; j++)
                {
                    out.println("receive");
                    out.println(String.format("\t\t%d) %-35s\t(€%-5s)", j, appetizers[0][j], appetizers[1][j]));
                }
                while(true)
                {
                    try
                    {
                        out.println("send");
                        int decision=Integer.parseInt(in.readLine());
                        r.add(String.format("\t\t%-35s\t(€%-5s)", appetizers[0][decision], appetizers[1][decision]));
                        bill+=Double.parseDouble(appetizers[1][decision]);
                        break;
                    }
                    catch(NumberFormatException e)
                    {
                        out.println("receive");
                        out.println("Deve inserire un numero corrispondente alla sua scelta");
                    }
                    catch(IndexOutOfBoundsException e)
                    {
                        out.println("receive");
                        out.println("Il numero che ha inserito non corrisponde a nessuna scelta");
                    }
                    catch(IOException e)
                    {
                        e.printStackTrace();
                    }
                }

                r.add("\tPrimo piatto: ");
                out.println("receive");
                out.println("\tPrimi piatti: ");
                for(int j=0; j<firstCourses[0].length; j++)
                {
                    out.println("receive");
                    out.println(String.format("\t\t%d) %-35s\t(€%-5s)", j, firstCourses[0][j], firstCourses[1][j]));
                }
                while(true)
                {
                    try
                    {
                        out.println("send");
                        int decision=Integer.parseInt(in.readLine());
                        r.add(String.format("\t\t%-35s\t(€%-5s)", firstCourses[0][decision], firstCourses[1][decision]));
                        bill+=Double.parseDouble(firstCourses[1][decision]);
                        break;
                    }
                    catch(NumberFormatException e)
                    {
                        out.println("receive");
                        out.println("Deve inserire un numero corrispondente alla sua scelta");
                    }
                    catch(IndexOutOfBoundsException e)
                    {
                        out.println("receive");
                        out.println("Il numero che ha inserito non corrisponde a nessuna scelta");
                    }
                    catch(IOException e)
                    {
                        e.printStackTrace();
                    }
                }

                r.add("\tSecondo piatto: ");
                out.println("receive");
                out.println("\tSecondi piatti: ");
                for(int j=0; j<secondCourses[0].length; j++)
                {
                    out.println("receive");
                    out.println(String.format("\t\t%d) %-35s\t(€%-5s)", j, secondCourses[0][j], secondCourses[1][j]));
                }
                while(true)
                {
                    try
                    {
                        out.println("send");
                        int decision=Integer.parseInt(in.readLine());
                        r.add(String.format("\t\t%-35s\t(€%-5s)", secondCourses[0][decision], secondCourses[1][decision]));
                        bill+=Double.parseDouble(secondCourses[1][decision]);
                        break;
                    }
                    catch(NumberFormatException e)
                    {
                        out.println("receive");
                        out.println("Deve inserire un numero corrispondente alla sua scelta");
                    }
                    catch(IndexOutOfBoundsException e)
                    {
                        out.println("receive");
                        out.println("Il numero che ha inserito non corrisponde a nessuna scelta");
                    }
                    catch(IOException e)
                    {
                        e.printStackTrace();
                    }
                }

                r.add("\tDessert: ");
                out.println("receive");
                out.println("\tDesserts: ");
                for(int j=0; j<desserts[0].length; j++)
                {
                    out.println("receive");
                    out.println(String.format("\t\t%d) %-35s\t(€%-5s)", j, desserts[0][j], desserts[1][j]));
                }
                while(true)
                {
                    try
                    {
                        out.println("send");
                        int decision=Integer.parseInt(in.readLine());
                        r.add(String.format("\t\t%-35s\t(€%-5s)", desserts[0][decision], desserts[1][decision]));
                        bill+=Double.parseDouble(desserts[1][decision]);
                        break;
                    }
                    catch(NumberFormatException e)
                    {
                        out.println("receive");
                        out.println("Deve inserire un numero corrispondente alla sua scelta");
                    }
                    catch(IndexOutOfBoundsException e)
                    {
                        out.println("receive");
                        out.println("Il numero che ha inserito non corrisponde a nessuna scelta");
                    }
                    catch(IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }

            boolean exit=false;
            while(!exit)
            {
                out.println("receive");
                out.println("Vuole delle bibite?\t(s/n)");
                out.println("send");
                try
                {
                    switch(in.readLine().toLowerCase())
                    {
                        case "n": exit=true; break;
                        case "s": exit=true; bill+=addDrinks(r); break;
                        default: out.println("receive"); out.println("Non ho capito");
                    }
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
            }

            LocalDate reservationDay, dayLimit=LocalDate.now().plusMonths(1);
            LocalTime reservationTime, timeLimit=LocalTime.of(21,0);

            out.println("receive");
            out.println("Inserisca la data per la prenotazione (aaaa-mm-gg) accettiamo prenotazioni fino al "+dayLimit.toString()+" escluso");
            while(true)
            {
                try
                {
                    out.println("send");
                    reservationDay=LocalDate.parse(in.readLine());
                    if(reservationDay.isAfter(LocalDate.now()) && reservationDay.isBefore(dayLimit))
                        break;

                    out.println("receive");
                    out.println("La data non è valida, non può essere nel passato nè oltre il limite");
                }
                catch(DateTimeParseException e)
                {
                    out.println("receive");
                    out.println("Il formato della data non è valido, dev\'essere aaaa-mm-gg, e i numeri inferiori a 10 devono avere lo 0 che li preceda");
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
            }
            r.add("Data prenotazione: "+reservationDay.toString());

            out.println("receive");
            out.println("Per che ora vuole prenotare? (hh:mm) Accettiamo prenotazioni fino alle "+timeLimit.toString()+" escluse");
            while(true)
            {
                try
                {
                    out.println("send");
                    reservationTime=LocalTime.parse(in.readLine());
                    if(reservationTime.isBefore(timeLimit))
                        break;
                    out.println("receive");
                    out.println("L\'ora che ha inserito non è valida, non può essere nel passato nè oltre il limite");
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
                catch(DateTimeParseException e)
                {
                    out.println("receive");
                    out.println("Il formato dell'ora che ha inserito non è valido, dev\'essere hh:mm");
                }
            }
            r.add("Ora arrivo: "+reservationTime.toString());


            r.add("");
            r.add("TOTALE: €"+String.valueOf(bill));

            return r;
        };

        Function<Integer, LinkedList<String>> apartment=(Integer integer) ->
        {
            System.out.println("Client #"+clientNumber+" has choosen apartment");

            LinkedList<String> r=new LinkedList<>();
            double bill=0;

            LocalDate arrival, departure, arrivalLimit=LocalDate.of(LocalDate.now().plusYears(1).getYear(), 12, 1), departureLimit=LocalDate.of(LocalDate.now().plusYears(1).getYear(), 12, 31);

            r.add("Appartamento: ");

            out.println("receive");
            out.println("Offriamo esclusivamente prenotazioni settimanali, inserisca la data di arrivo (aaaa-mm-gg). Accettiamo prenotazioni fino al "+arrivalLimit.toString()+" escluso");
            while(true)
            {
                try
                {
                    out.println("send");
                    arrival=LocalDate.parse(in.readLine());
                    if(arrival.isAfter(LocalDate.now()) && arrival.isBefore(arrivalLimit))
                        break;
                    out.println("receive");
                    out.println("La data non è valida: non può essere nel passato nè oltre il limite");
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
                catch(DateTimeParseException e)
                {
                    out.println("receive");
                    out.println("La data deve avere formato aaaa-mm-gg (mese e giorno devono includere lo 0 se inferiori a 10)");
                }
            }

            r.add("Data di arrivo: "+arrival.toString());

            out.println("receive");
            out.println("Quante settimane si vuole trattenere? Accettiamo partenze fino al "+departureLimit.toString());
            int weeks;
            while(true)
            {
                try
                {
                    out.println("send");
                    weeks=Integer.parseInt(in.readLine());
                    departure=arrival.plusWeeks(weeks);
                    if(departure.isBefore(departureLimit))
                        break;

                    out.println("receive");
                    out.println("Inserisca un numero di settimane minore, in modo da rientrare nel limite");
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
                catch(NumberFormatException e)
                {
                    out.println("receive");
                    out.println("Deve inserire un numero");
                }
            }

            r.add("Data di partenza: "+departure.toString());

            out.println("receive");
            out.println("Selezioni il tipo di appartamento (prezzo per settimana)");
            for(int i=0; i<apartmentsList[0].length; i++)
            {
                out.println("receive");
                out.println(String.format("%d) %-30s\t(€%-5s)", i, apartmentsList[0][i], apartmentsList[1][i]));
            }
            while(true)
            {
                try
                {
                    out.println("send");
                    int decision=Integer.parseInt(in.readLine());
                    r.add(String.format("Tipologia di appartamento: %-30s\t(€%-5s/settimana)", apartmentsList[0][decision], apartmentsList[1][decision]));
                    bill+=Double.parseDouble(apartmentsList[1][decision])*weeks;
                    break;
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
                catch(NumberFormatException e)
                {
                    out.println("receive");
                    out.println("Deve inserire un numero che corrisponda alla sua scelta");
                }
                catch(IndexOutOfBoundsException e)
                {
                    out.println("receive");
                    out.println("Il numero che ha inserito non corrisponde a nessuna opzione");
                }
            }
            r.add("");
            r.add("TOTALE: "+String.valueOf(bill));

            return r;
        };

        operations.put("Pizzeria", pizzeria);
        operations.put("Ristorante", restaurant);
        operations.put("Hotel", hotel);
        operations.put("Appartamento", apartment);
    }

    private double addDrinks(LinkedList<String> r)
    {
        r.add("Bibite: ");
        int n, l=drinksList[0].length;
        double bill=0;
        while(true)
        {
            try
            {
                out.println("receive");
                out.println("Quante bibite vuole?");
                out.println("send");
                n=Integer.parseInt(in.readLine());
                break;
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
            catch(NumberFormatException e)
            {
                out.println("receive");
                out.println("Deve inserire un numero");
            }
        }
        r.add("Quantita bibite: "+n);
        for(int i=0; i<n; i++)
        {
            out.println("receive");
            out.println("Che bibita vuole? (#"+(i+1)+")");
            for(int j=0; j<l; j++)
            {
                out.println("receive");
                out.println(String.format("%d) %-25s\t(€%-4s)", j, drinksList[0][j], drinksList[1][j]));
            }
            int decision;
            while(true)
            {
                try
                {
                    out.println("send");
                    decision=Integer.parseInt(in.readLine());
                    // r.add(drinksList[0][decision]);
                    r.add(String.format("\tBibita #%d: %-15s\t(€%-4s)", i+1, drinksList[0][decision], drinksList[1][decision]));
                    bill+=Double.parseDouble(drinksList[1][decision]);
                    break;

                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
                catch(NumberFormatException e)
                {
                    out.println("receive");
                    out.println("Deve inserire un numero che corrisponda alla bibita selezionata");
                }
                catch(IndexOutOfBoundsException e)
                {
                    out.println("receive");
                    out.println("Il numero che ha inserito non corrisponde a nessuna opzione");
                }
            }
        }

        return bill;
    }

    public LinkedList<String> startDialog()
    {
        LinkedList<String> options=new LinkedList<>();
        operations.forEach((k,v)->options.add(k));
        for(int i=0; i<options.size(); i++)
        {
            out.println("receive");
            // out.println(1);
            out.println(String.format("%d) %s", i, options.get(i)));
        }

        LinkedList<String> receipt;
        while(true)
        {
            try
            {
                out.println("send");
                receipt=operations.get(options.get(Integer.parseInt(in.readLine()))).apply(0);
                break;
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
            catch(NumberFormatException e)
            {
                out.println("receive");
                // out.println("2");
                // out.println("NO");
                out.println("Deve inserire un numero corrispondente alla sua scelta");
            }
            catch(IndexOutOfBoundsException e)
            {
                // out.println("NO");
                out.println("receive");
                out.println("Il numero che ha selezionato è al di fuori dell'intervallo delle scelte disponibili");
            }
        }

        return receipt;
    }
}
