package at.fhv.teamh.hauptsach_ticket.backend.application

import at.fhv.teamh.hauptsach_ticket.backend.domain.*
import java.time.LocalDate

@Suppress("kotlin:S1192", "MemberVisibilityCanBePrivate")
class TestData(removeOldData: Boolean = true) {
    val defaultAccount = DefaultData.account
    val fhVorarlberg = Organiser(
        name = "FH Vorarlberg",
        address = "Dornbirn",
        eMail = "example@fhv.at",
        telephone = "1234567890"
    )
    val jugendvereinLustenau = Organiser(
        name = "Jugendverein Szene Lustenau",
        address = "6890 Lustenau",
        eMail = "example@fhv.at",
        telephone = "64539574534"
    )
    val bregenzerFestspiele = Organiser(
        name = "Bregenzer Festspiele GmbH",
        address = "6900 Bregenz, Platz der Wiener Symphoniker",
        eMail = "example@fhv.at",
        telephone = "+43 6674 4076"
    )

    val kulturhausDornbirn = Organiser(
        name = "Kulturhaus Dornbirn",
        address = "6850 Dornbirn",
        eMail = "example@kulturhaus.at",
        telephone = "+43 5572 21933"
    )

    val feldkircherFestspiele = Organiser(
        name = "Feldkircher Festspiele",
        address = "6800 Feldkirch",
        eMail = "example@feldkircher-festspiele.at",
        telephone = "+43 5522 78909"
    )

    val hohenemsKultur = Organiser(
        name = "Kultur Hohenems",
        address = "6845 Hohenems",
        eMail = "example@hohenemskultur.at",
        telephone = "+43 5576 65011"
    )

    val theaterAmSaumarkt = Organiser(
        name = "Theater am Saumarkt",
        address = "6845 Hohenems",
        eMail = "example@theater-am-saumarkt.at",
        telephone = "+43 5576 76777"
    )

    val theaterKosmos = Organiser(
        name = "Theater Kosmos",
        address = "6845 Hohenems",
        eMail = "example@theater-kosmos.at",
        telephone = "+43 5576 74740"
    )

    val vorarlbergerLandestheater = Organiser(
        name = "Vorarlberger Landestheater",
        address = "6900 Bregenz",
        eMail = "example@landestheater.at",
        telephone = "+43 5574 42868"
    )

    val bodenseearena = Organiser(
        name = "Bodensee-Arena",
        address = "6900 Bregenz",
        eMail = "example@bodensee-arena.at",
        telephone = "+43 5574 41320"
    )

    val stadtGoetzis = Organiser(
        name = "Veranstaltungen am Garnmarkt",
        address = "6840 Götzis",
        eMail = "example@veranstaltungen-am-ganrmarkt.at",
        telephone = "+43 5576 76757"
    )


    val lectures =
        Series(name = "Vorträge des Feilhauers", artist = "Thomas Feilhauer", organiser = fhVorarlberg)
    val szeneOpenAir =
        Series(name = "Szene Open Air", artist = "Penetrante Sorte", organiser = jugendvereinLustenau)
    val festspiele =
        Series(name = "Bregenzer Festspiele", artist = "Wiener symphoniker", organiser = bregenzerFestspiele)
    val operettenFestspiele =
        Series(name = "Operettenfestspiele", artist = "Feldkircher Oparetts", organiser = feldkircherFestspiele)
    val hofsteigKultur =
        Series(name = "Hofsteig Kultur", artist = "DreierTalk", organiser = hohenemsKultur)
    val kosmosKonzerte =
        Series(name = "Kosmos Konzerte", artist = "Skillet", organiser = theaterKosmos)
    val vorarlbergJazz =
        Series(name = "Vorarlberg Jazz", artist = "JazzPerator", organiser = vorarlbergerLandestheater)
    val bodenseeClassic =
        Series(name = "Bodensee Classic", artist = "Feilinator", organiser = bodenseearena)
    val kulturhausDornbirnSeries2 =
        Series(name = "Konzerte im Kulturhaus", artist = "Windows Forever", organiser = kulturhausDornbirn)
    val kulturhausDornbirnSeries3 =
        Series(name = "Comedy im Kulturhaus", artist = "Julian Linux", organiser = kulturhausDornbirn)

    val theaterAmSaumarktSeries2 =
        Series(name = "Gastspiele im Theater am Saumarkt", artist = "Beans for Life", organiser = theaterAmSaumarkt)
    val theaterAmSaumarktSeries3 =
        Series(name = "Kindertheater im Theater am Saumarkt", artist = "Batu der Akku", organiser = theaterAmSaumarkt)
    val seminar =
        Series(name = "Vorträge des Feilhauers", artist = "Thomas Feilhauer", organiser = fhVorarlberg)
    val szeneOpenAir2023 =
        Series(name = "Szene Open Air", artist = "Penetrante Sorte", organiser = jugendvereinLustenau)
    val festspiele2023 =
        Series(name = "Bregenzer Festspiele", artist = "Tristan Beans", organiser = bregenzerFestspiele)
    val operettenFestspiele2023 =
        Series(name = "Operettenfestspiele", artist = "Feldkircher Oparetts", organiser = feldkircherFestspiele)
    val hofsteigKultur2023 =
        Series(name = "Hofsteig Kultur", artist = "Dario Dailymaster", organiser = hohenemsKultur)
    val kosmosKonzerte2023 =
        Series(name = "Kosmos Konzerte", artist = "One Republic", organiser = theaterKosmos)
    val vorarlbergJazz2023 =
        Series(name = "Vorarlberg Jazz", artist = "JazzPerator", organiser = vorarlbergerLandestheater)
    val bodenseeClassic2023 =
        Series(name = "Bodensee Classic", artist = "Feilinator", organiser = bodenseearena)
    val worldTourSeries =
        Series(name = "World Tour", artist = "BTS", organiser = stadtGoetzis)
    val festeAmGarnmarktSeries =
        Series(name = "Feste am Garnmarkt", artist = "Stadt Götzis", organiser = stadtGoetzis)
    val wordlTourRock =
        Series(name = "World Tour", artist = "ACDC", organiser = stadtGoetzis)
    val lecturesGruppeH =
        Series(name = "Wo ist Batu?", artist = "Achim Undertaker", organiser = fhVorarlberg)
    val gruppeHPushen =
        Series(name = "Batu Push doch!!!", artist = "10 Stones and 5Pounds", organiser = fhVorarlberg)
    val gruppeHDaily=
        Series(name = "3 Uhr Daily", artist = "Batu der Nacht,der Sonntags nix zu tun hat", organiser = fhVorarlberg)

    val kulturhausDornbirnLocation =
        Location(address = "Rathausplatz 1, 6850 Dornbirn", building = "Main Building", room = "102")
    val szeneLustenauLocation =
        Location(address = "Kneippstrasse 8, A-6890 Lustenau", building = "n.A.", room = "n.A.")
    val festspieleBregenzLocation =
        Location(address = "6900 Bregenz, Platz der Wiener Symphoniker", building = "n.A.", room = "105")
    val theaterKellerLocation =
        Location(address = "Kaiserstraße 76, 6845 Hohenems", building = "Theater Keller", room = "210")
    val hofsteigsaalLocation =
        Location(address = "Hofsteigstraße 54, 6971 Hard", building = "Hofsteigsaal", room = "n.A.")
    val montforthausFeldkirchLocation =
        Location(address = "Montfortplatz 1, 6800 Feldkirch", building = "Montforthaus", room = "n.A.")
    val kammgarnHardLocation =
        Location(address = "Werkstraße 11, 6971 Hard", building = "Kammgarn", room = "n.A.")

    val kulturhausDornbirnLocation2 =
        Location(address = "Rathausplatz 1, 6850 Dornbirn", building = "Main Building", room = "001")
    val kulturhausDornbirnLocation3 =
        Location(address = "Rathausplatz 1, 6850 Dornbirn", building = "Small Hall", room = "201")
    val kulturhausDornbirnLocation4 =
        Location(address = "Rathausplatz 1, 6850 Dornbirn", building = "Main Building", room = "302")

    val theaterAmSaumarktLocation2 =
        Location(address = "Rheinstraße 7, 6845 Hohenems", building = "n.A.", room = "n.A.")
    val theaterAmSaumarktLocation3 =
        Location(address = "Rheinstraße 7, 6845 Hohenems", building = "n.A.", room = "n.A.")
    val theaterAmSaumarktLocation4 =
        Location(address = "Rheinstraße 7, 6845 Hohenems", building = "n.A.", room = "n.A.")

    val fhVorarlbergLocation4 =
        Location(address = "Campus V, Hochschulstraße 1, 6850 Dornbirn", building = "n.A.", room = "n.A.")
    val fhVorarlbergLocation5 =
        Location(address = "Bahnhofstraße 12, 6850 Dornbirn", building = "n.A.", room = "n.A.")

    val fhVorarlbergLocation6 =
        Location(address = "Campus V, Hochschulstraße 1, 6850 Dornbirn", building = "V", room = "113")

    val fhVorarlbergLocation7 =
        Location(address = "Bahnhofstraße 12, 6850 Dornbirn", building = "B", room = "203")

    val fhVorarlbergLocation8 =
        Location(address = "Campus V, Hochschulstraße 1, 6850 Dornbirn", building = "U", room = "328")

    val fhVorarlbergLocation9 =
        Location(address = "Bahnhofstraße 12, 6850 Dornbirn", building = "U", room = "327")

    val kulturhausDornbirnLocation5 =
        Location(address = "Schulgasse 3, 6850 Dornbirn", building = "Saal", room = "n.A.")

    val kulturhausDornbirnLocation6 =
        Location(address = "Konrad-Duden-Straße 37, 6850 Dornbirn", building = "n.A.", room = "n.A.")

    val theaterAmSaumarktLocation5 =
        Location(address = "Bahnhofstraße 11, 6845 Hohenems", building = "Saal", room = "n.A.")

    val theaterAmSaumarktLocation6 =
        Location(address = "Am Markt 2, 6845 Hohenems", building = "Keller", room = "n.A.")

    val festeAmGarnmarktLocation =
        Location(address = "Am Garnmarkt, 6840 Götzis", building = "n.A.", room = "n.A.")




    val kulturhausLecture = Event(
        name = "Vortrag im Kulturhaus",
        date = LocalDate.now(),
        series = lectures,
        location = kulturhausDornbirnLocation,
        genre = "Vortrag",
    )
    val szeneDay1 = Event(
        name = "Szene Open Air Lustenau Day 1",
        date = LocalDate.now(),
        series = szeneOpenAir,
        location = szeneLustenauLocation,
        genre = "Festival",
    )
    val szeneDay2 = Event(
        name = "Szene Open Air Lustenau Day 2",
        date = LocalDate.now(),
        series = szeneOpenAir,
        location = szeneLustenauLocation,
        genre = "Festival",
    )
    val festspieleBregenz = Event(
        name = "Festspiele Bregenz",
        date = LocalDate.now(),
        series = festspiele,
        location = festspieleBregenzLocation,
        genre = "Festspiel",
    )

    val theaterKellerEvent = Event(
        name = "Theater Keller Vorarlberg",
        date = LocalDate.now(),
        series = operettenFestspiele,
        location = theaterKellerLocation,
        genre = "Vortrag",
    )

    val hofsteigsaalEvent = Event(
        name = "Hofsteigsaal Hörbranz",
        date = LocalDate.now(),
        series = hofsteigKultur,
        location = hofsteigsaalLocation,
        genre = "Festival",
    )

    val montforthausFeldkirchEvent = Event(
        name = "Montforthaus Feldkirch",
        date = LocalDate.now(),
        series = kosmosKonzerte,
        location = montforthausFeldkirchLocation,
        genre = "Festspiel",
    )

    val kammgarnHardEvent = Event(
        name = "Kammgarn Hard",
        date = LocalDate.now(),
        series = vorarlbergJazz,
        location = kammgarnHardLocation,
        genre = "Vortrag",
    )

    val kulturhausDornbirnEvent2 = Event(
        name = "Kulturhaus Dornbirn",
        date = LocalDate.now(),
        series = bodenseeClassic,
        location = kulturhausDornbirnLocation2,
        genre = "Festival",
    )

    val kulturhausDornbirnEvent3 = Event(
        name = "Kulturhaus Dornbirn",
        date = LocalDate.now(),
        series = kulturhausDornbirnSeries2,
        location = kulturhausDornbirnLocation3,
        genre = "Festspiel",
    )

    val kulturhausDornbirnEvent4 = Event(
        name = "Kulturhaus Dornbirn",
        date = LocalDate.now(),
        series = kulturhausDornbirnSeries3,
        location = kulturhausDornbirnLocation4,
        genre = "Vortrag",
    )

    val theaterAmSaumarktEvent2 = Event(
        name = "Theater am Saumarkt",
        date = LocalDate.now(),
        series = theaterAmSaumarktSeries2,
        location = theaterAmSaumarktLocation2,
        genre = "Festival",
    )

    val theaterAmSaumarktEvent3 = Event(
        name = "Theater am Saumarkt",
        date = LocalDate.now(),
        series = theaterAmSaumarktSeries3,
        location = theaterAmSaumarktLocation3,
        genre = "Festspiel",
    )

    val theaterAmSaumarktEvent4 = Event(
        name = "Theater am Saumarkt",
        date = LocalDate.now(),
        series = szeneOpenAir2023,
        location = theaterAmSaumarktLocation4,
        genre = "Vortrag",
    )

    val fhVorarlbergEvent4 = Event(
        name = "FH Vorarlberg",
        date = LocalDate.now(),
        series = seminar,
        location = fhVorarlbergLocation4,
        genre = "Festival",
    )

    val fhVorarlbergEvent5 = Event(
        name = "FH Vorarlberg",
        date = LocalDate.now(),
        series = seminar,
        location = fhVorarlbergLocation5,
        genre = "Festspiel",
    )

    val fhVorarlbergEvent8 = Event(
        name = "FH Vorarlberg - Event 8",
        date = LocalDate.now(),
        series = operettenFestspiele2023,
        location = fhVorarlbergLocation6,
        genre = "Vortrag",
    )

    val fhVorarlbergEvent9 = Event(
        name = "FH Vorarlberg - Event 9",
        date = LocalDate.now(),
        series = hofsteigKultur2023,
        location = fhVorarlbergLocation7,
        genre = "Vortrag",
    )

    val kulturhausDornbirnEvent6 = Event(
        name = "Kulturhaus Dornbirn - Event 6",
        date = LocalDate.now(),
        series = festspiele2023,
        location = kulturhausDornbirnLocation5,
        genre = "Festspiel",
    )

    val kulturhausDornbirnEvent7 = Event(
        name = "Kulturhaus Dornbirn - Event 7",
        date = LocalDate.now(),
        series = kosmosKonzerte2023,
        location = kulturhausDornbirnLocation6,
        genre = "Festival",
    )

    val theaterAmSaumarktEvent5 = Event(
        name = "Theater am Saumarkt",
        date = LocalDate.now(),
        series = vorarlbergJazz2023,
        location = theaterAmSaumarktLocation5,
        genre = "Festspiel",
    )

    val theaterAmSaumarktEvent6 = Event(
        name = "Theater am Saumarkt",
        date = LocalDate.now(),
        series = bodenseeClassic2023,
        location = theaterAmSaumarktLocation6,
        genre = "Festspiel",
    )

    val fhVorarlbergGruppeH = Event(
        name = "FH Vorarlberg",
        date = LocalDate.now(),
        series = lecturesGruppeH,
        location = fhVorarlbergLocation5,
        genre = "Vortrag",
    )

    val fhVorarlbergGruppeH2 = Event(
        name = "FH Vorarlberg",
        date = LocalDate.now(),
        series = gruppeHPushen,
        location = fhVorarlbergLocation8,
        genre = "Vortrag",
    )

    val fhVorarlbergGruppeH3 = Event(
        name = "FH Vorarlberg",
        date = LocalDate.now(),
        series = gruppeHDaily,
        location = fhVorarlbergLocation9,
        genre = "Vortrag",
    )

    val festeAmGarnmarktSommer = Event(
        name = "Weinfest am Garnmarkt",
        date = LocalDate.now(),
        series = festeAmGarnmarktSeries,
        location = festeAmGarnmarktLocation,
        genre = "Festival",
    )
    val festeAmGarnmarktSommerKonzert = Event(
        name = "ACDC Konzert am Garnmarkt",
        date = LocalDate.now(),
        series = wordlTourRock,
        location = festeAmGarnmarktLocation,
        genre = "Festival",
    )

    val festeAmGarnmarktWinter = Event(
        name = "Weihnachtsmarkt am Garnmarkt",
        date = LocalDate.now(),
        series = festeAmGarnmarktSeries,
        location = festeAmGarnmarktLocation,
        genre = "Festival",
    )

    val festeAmGarnmarktFruehlingsKonzert = Event(
        name = "BTS Konzert am Garnmarkt",
        date = LocalDate.now(),
        series = worldTourSeries,
        location = festeAmGarnmarktLocation,
        genre = "Festival",
    )




    val kulturhausLectureTicketcategory1 =
        TicketCategory(
            name = "Standing Ticket", event = kulturhausLecture, price = 19.95, totalTickets = 25
        )

    val kulturhausLectureTicketcategory2 =
        TicketCategory(
            name = "Sitting Ticket", event = kulturhausLecture, price = 24.95, totalTickets = 35
        )

    val szeneDay1TicketCategory1 =
        TicketCategory(
            name = "Standard Ticket", event = szeneDay1, price = 19.95, totalTickets = 100
        )

    val szeneDay2TicketCategory1 =
        TicketCategory(
            name = "Standard Ticket", event = szeneDay2, price = 19.95, totalTickets = 100
        )

    val festspieleBregenzTicketcategory1 =
        TicketCategory(
            name = "Standing Ticket", event = festspieleBregenz, price = 29.95, totalTickets = 50
        )

    val festspieleBregenzTicketcategory2 =
        TicketCategory(
            name = "Sitting Ticket", event = festspieleBregenz, price = 35.95, totalTickets = 150
        )

    val theaterKellerEventTicketcategory1 =
        TicketCategory(
            name = "Sitting Ticket", event = theaterKellerEvent, price = 31.95, totalTickets = 150
        )

    val theaterKellerEventTicketcategory2 =
        TicketCategory(
            name = "VIP Ticket", event = theaterKellerEvent, price = 40.95, totalTickets = 30
        )

    val hofsteigsaalEventTicketcategory1 =
        TicketCategory(
            name = "Sitting Ticket", event = hofsteigsaalEvent, price = 29.99, totalTickets = 200
        )

    val montforthausFeldkirchEventTicketcategory1 =
        TicketCategory(
            name = "Standing Ticket", event = montforthausFeldkirchEvent, price = 25.99, totalTickets = 100
        )

    val montforthausFeldkirchEventTicketcategory2 =
        TicketCategory(
            name = "Sitting Ticket", event = montforthausFeldkirchEvent, price = 32.00, totalTickets = 170
        )

    val kammgarnHardEventTicketcategory1 =
        TicketCategory(
            name = "Standing Ticket", event = kammgarnHardEvent, price = 19.99, totalTickets = 200
        )

    val kammgarnHardEventTicketcategory2 =
        TicketCategory(
            name = "Sitting Ticket", event = kammgarnHardEvent, price = 24.99, totalTickets = 80
        )
    val kulturhausDornbirnEvent2Ticketcategory1 =
        TicketCategory(
            name = "Standing Ticket", event = kulturhausDornbirnEvent2, price = 29.95, totalTickets = 150
        )

    val kulturhausDornbirnEvent2Ticketcategory2 =
        TicketCategory(
            name = "Sitting Ticket", event = kulturhausDornbirnEvent2, price = 39.99, totalTickets = 200
        )

    val kulturhausDornbirnEvent2Ticketcategory3 =
        TicketCategory(
            name = "VIP", event = kulturhausDornbirnEvent2, price = 49.99, totalTickets = 30
        )

    val kulturhausDornbirnEvent3Ticketcategory1 =
        TicketCategory(
            name = "Standing Ticket", event = kulturhausDornbirnEvent3, price = 23.95, totalTickets = 100
        )

    val kulturhausDornbirnEvent3Ticketcategory2 =
        TicketCategory(
            name = "Sitting Ticket", event = kulturhausDornbirnEvent3, price = 28.95, totalTickets = 350
        )
    val kulturhausDornbirnEvent4Ticketcategory1 =
        TicketCategory(
            name = "Standing Ticket", event = kulturhausDornbirnEvent4, price = 27.95, totalTickets = 70
        )

    val kulturhausDornbirnEvent4Ticketcategory2 =
        TicketCategory(
            name = "Sitting Ticket", event = kulturhausDornbirnEvent4, price = 32.95, totalTickets = 130
        )
    val theaterAmSaumarktEvent2Ticketcategory1 =
        TicketCategory(
            name = "Standing Ticket", event = theaterAmSaumarktEvent2, price = 24.90, totalTickets = 90
        )

    val theaterAmSaumarktEvent2Ticketcategory2 =
        TicketCategory(
            name = "Sitting Ticket", event = theaterAmSaumarktEvent2, price = 36.90, totalTickets = 120
        )
    val theaterAmSaumarktEvent3Ticketcategory1 =
        TicketCategory(
            name = "Standing Ticket", event = theaterAmSaumarktEvent3, price = 29.95, totalTickets = 50
        )

    val theaterAmSaumarktEvent3Ticketcategory2 =
        TicketCategory(
            name = "Sitting Ticket", event = theaterAmSaumarktEvent3, price = 35.95, totalTickets = 150
        )
    val theaterAmSaumarktEvent4Ticketcategory1 =
        TicketCategory(
            name = "Standing Ticket", event = theaterAmSaumarktEvent4, price = 31.99, totalTickets = 20
        )

    val theaterAmSaumarktEvent4Ticketcategory2 =
        TicketCategory(
            name = "Sitting Ticket", event = theaterAmSaumarktEvent4, price = 36.99, totalTickets = 100
        )
    val fhVorarlbergEvent4Ticketcategory1 =
        TicketCategory(
            name = "Standing Ticket", event = fhVorarlbergEvent4, price = 19.95, totalTickets = 30
        )

    val fhVorarlbergEvent4Ticketcategory2 =
        TicketCategory(
            name = "Sitting Ticket", event = fhVorarlbergEvent4, price = 25.95, totalTickets = 20
        )
    val fhVorarlbergEvent5Ticketcategory1 =
        TicketCategory(
            name = "Standing Ticket", event = fhVorarlbergEvent5, price = 19.95, totalTickets = 50
        )

    val fhVorarlbergEvent5Ticketcategory2 =
        TicketCategory(
            name = "Sitting Ticket", event = fhVorarlbergEvent5, price = 25.95, totalTickets = 40
        )
    val fhVorarlbergEvent8Ticketcategory1 =
        TicketCategory(
            name = "Standing Ticket", event = fhVorarlbergEvent8, price = 20.21, totalTickets = 70
        )

    val fhVorarlbergEvent8Ticketcategory2 =
        TicketCategory(
            name = "Sitting Ticket", event = fhVorarlbergEvent8, price = 30.00, totalTickets = 50
        )
    val fhVorarlbergEvent9Ticketcategory1 =
        TicketCategory(
            name = "Standing Ticket", event = fhVorarlbergEvent9, price = 23.90, totalTickets = 80
        )

    val fhVorarlbergEvent9Ticketcategory2 =
        TicketCategory(
            name = "Sitting Ticket", event = fhVorarlbergEvent9, price = 32.90, totalTickets = 60
        )
    val kulturhausDornbirnEvent6Ticketcategory1 =
        TicketCategory(
            name = "Standing Ticket", event = kulturhausDornbirnEvent6, price = 39.99, totalTickets = 100
        )

    val kulturhausDornbirnEvent6Ticketcategory2 =
        TicketCategory(
            name = "Sitting Ticket", event = kulturhausDornbirnEvent6, price = 49.99, totalTickets = 150
        )
    val kulturhausDornbirnEvent7Ticketcategory1 =
        TicketCategory(
            name = "Standing Ticket", event = kulturhausDornbirnEvent7, price = 37.90, totalTickets = 70
        )

    val kulturhausDornbirnEvent7Ticketcategory2 =
        TicketCategory(
            name = "Sitting Ticket", event = kulturhausDornbirnEvent7, price = 45.90, totalTickets = 120
        )
    val theaterAmSaumarktEvent5Ticketcategory1 =
        TicketCategory(
            name = "Standing Ticket", event = theaterAmSaumarktEvent5, price = 22.20, totalTickets = 150
        )

    val theaterAmSaumarktEvent5Ticketcategory2 =
        TicketCategory(
            name = "Sitting Ticket", event = theaterAmSaumarktEvent5, price = 30.10, totalTickets = 90
        )
    val fhVorarlbergGruppeHTicketcategory1 =
        TicketCategory(
            name = "Sitting Ticket", event = fhVorarlbergGruppeH, price = 69.99, totalTickets = 200
        )
    val fhVorarlbergGruppeH2Ticketcategory1 =
        TicketCategory(
            name = "Sitting Ticket", event = fhVorarlbergGruppeH2, price = 69.99, totalTickets = 200
        )
    val fhVorarlbergGruppeH3Ticketcategory1 =
        TicketCategory(
            name = "Stand-up seats", event = fhVorarlbergGruppeH3, price = 42.00, totalTickets = 200
        )
    val theaterAmSaumarktEvent6Ticketcategory1 =
        TicketCategory(
            name = "Standing Ticket", event = theaterAmSaumarktEvent6, price = 15.99, totalTickets = 200
        )

    val theaterAmSaumarktEvent6Ticketcategory2 =
        TicketCategory(
            name = "Sitting Ticket", event = theaterAmSaumarktEvent6, price = 20.00, totalTickets = 150
        )
    val festeAmGarnmarktSommerTicketcategory1 =
        TicketCategory(
            name = "Standing Ticket", event = festeAmGarnmarktSommer, price = 20.99, totalTickets = 300
        )

    val festeAmGarnmarktSommerTicketcategory2 =
        TicketCategory(
            name = "Sitting Ticket", event = festeAmGarnmarktSommer, price = 39.99, totalTickets = 200
        )

    val festeAmGarnmarktSommerKonzertTicketcategory1 =
        TicketCategory(
            name = "Standing Ticket", event = festeAmGarnmarktSommerKonzert, price = 59.99, totalTickets = 400
        )

    val festeAmGarnmarktWinterTicketcategory1 =
        TicketCategory(
            name = "Standing Ticket", event = theaterAmSaumarktEvent6, price = 18.99, totalTickets = 300
        )

    val festeAmGarnmarktWinterTicketcategory2 =
        TicketCategory(
            name = "Sitting Ticket", event = theaterAmSaumarktEvent6, price = 30.00, totalTickets = 100
        )
    val festeAmGarnmarktFruehlingsKonzertTicketcategory1 =
        TicketCategory(
            name = "Standing Ticket", event = theaterAmSaumarktEvent6, price = 65.99, totalTickets = 400
        )


    val kulturhausCategory1TicketNumbers = (1..25).map {
        TicketNumber(number = it, ticketCategory = kulturhausLectureTicketcategory1)
    }
    val kulturhausCategory2TicketNumbers = (1..35).map {
        TicketNumber(number = it, ticketCategory = kulturhausLectureTicketcategory2)
    }

    val szeneDay1TicketCategory1TicketNumbers = (1..100).map {
        TicketNumber(number = it, ticketCategory = szeneDay1TicketCategory1)
    }
    val szeneDay2TicketCategory1TicketNumbers = (1..100).map {
        TicketNumber(number = it, ticketCategory = szeneDay2TicketCategory1)
    }

    val festspieleBregenzTicketcategory1TicketNumbers = (1..50).map {
        TicketNumber(number = it, ticketCategory = festspieleBregenzTicketcategory1)
    }
    val festspieleBregenzTicketcategory2TicketNumbers = (1..150).map {
        TicketNumber(number = it, ticketCategory = festspieleBregenzTicketcategory2)
    }

    val theaterKellerEventTicketcategory1TicketNumbers = (1..150).map {
        TicketNumber(number = it, ticketCategory = theaterKellerEventTicketcategory1)
    }
    val theaterKellerEventTicketcategory2TicketNumbers = (1..30).map {
        TicketNumber(number = it, ticketCategory = theaterKellerEventTicketcategory2)
    }

    val hofsteigsaalEventTicketcategory1TicketNumbers = (1..200).map {
        TicketNumber(number = it, ticketCategory =  hofsteigsaalEventTicketcategory1)
    }
    val montforthausFeldkirchEventTicketcategory1TicketNumbers = (1..100).map {
        TicketNumber(number = it, ticketCategory = montforthausFeldkirchEventTicketcategory1)
    }
    val montforthausFeldkirchEventTicketcategory2TicketNumbers = (1..170).map {
        TicketNumber(number = it, ticketCategory = montforthausFeldkirchEventTicketcategory2)
    }
    val kammgarnHardEventTicketcategory1TicketNumbers = (1..200).map {
        TicketNumber(number = it, ticketCategory = kammgarnHardEventTicketcategory1)
    }
    val kammgarnHardEventTicketcategory2TicketNumbers = (1..80).map {
        TicketNumber(number = it, ticketCategory = kammgarnHardEventTicketcategory2)
    }
    val kulturhausDornbirnEvent2Ticketcategory1TicketNumbers = (1..150).map {
        TicketNumber(number = it, ticketCategory = kulturhausDornbirnEvent2Ticketcategory1)
    }
    val kulturhausDornbirnEvent2Ticketcategory2TicketNumbers = (1..200).map {
        TicketNumber(number = it, ticketCategory = kulturhausDornbirnEvent2Ticketcategory2)
    }
    val kulturhausDornbirnEvent2Ticketcategory3TicketNumbers = (1..30).map {
        TicketNumber(number = it, ticketCategory = kulturhausDornbirnEvent2Ticketcategory3)
    }
    val kulturhausDornbirnEvent3Ticketcategory1TicketNumbers = (1..100).map {
        TicketNumber(number = it, ticketCategory = kulturhausDornbirnEvent3Ticketcategory1)
    }
    val kulturhausDornbirnEvent3Ticketcategory2TicketNumbers = (1..350).map {
        TicketNumber(number = it, ticketCategory = kulturhausDornbirnEvent3Ticketcategory2)
    }
    val kulturhausDornbirnEvent4Ticketcategory1TicketNumbers = (1..70).map {
        TicketNumber(number = it, ticketCategory = kulturhausDornbirnEvent4Ticketcategory1)
    }
    val kulturhausDornbirnEvent4Ticketcategory2TicketNumbers = (1..130).map {
        TicketNumber(number = it, ticketCategory = kulturhausDornbirnEvent4Ticketcategory2)
    }
    val theaterAmSaumarktEvent2Ticketcategory1TicketNumbers = (1..90).map {
        TicketNumber(number = it, ticketCategory = theaterAmSaumarktEvent2Ticketcategory1)
    }
    val theaterAmSaumarktEvent2Ticketcategory2TicketNumbers = (1..120).map {
        TicketNumber(number = it, ticketCategory = theaterAmSaumarktEvent2Ticketcategory2)
    }
    val theaterAmSaumarktEvent3Ticketcategory1TicketNumbers = (1..50).map {
        TicketNumber(number = it, ticketCategory = theaterAmSaumarktEvent3Ticketcategory1)
    }
    val theaterAmSaumarktEvent3Ticketcategory2TicketNumbers = (1..150).map {
        TicketNumber(number = it, ticketCategory = theaterAmSaumarktEvent3Ticketcategory2)
    }
    val theaterAmSaumarktEvent4Ticketcategory1TicketNumbers = (1..20).map {
        TicketNumber(number = it, ticketCategory = theaterAmSaumarktEvent4Ticketcategory1)
    }
    val theaterAmSaumarktEvent4Ticketcategory2TicketNumbers = (1..100).map {
        TicketNumber(number = it, ticketCategory = theaterAmSaumarktEvent4Ticketcategory2)
    }
    val fhVorarlbergEvent4Ticketcategory1TicketNumbers = (1..30).map {
        TicketNumber(number = it, ticketCategory = fhVorarlbergEvent4Ticketcategory1)
    }
    val fhVorarlbergEvent4Ticketcategory2TicketNumbers = (1..20).map {
        TicketNumber(number = it, ticketCategory = fhVorarlbergEvent4Ticketcategory2)
    }
    val fhVorarlbergEvent5Ticketcategory1TicketNumbers = (1..50).map {
        TicketNumber(number = it, ticketCategory = fhVorarlbergEvent5Ticketcategory1)
    }
    val fhVorarlbergEvent5Ticketcategory2TicketNumbers = (1..40).map {
        TicketNumber(number = it, ticketCategory = fhVorarlbergEvent5Ticketcategory2)
    }
    val fhVorarlbergEvent8Ticketcategory1TicketNumbers = (1..70).map {
        TicketNumber(number = it, ticketCategory = fhVorarlbergEvent8Ticketcategory1)
    }
    val fhVorarlbergEvent8Ticketcategory2TicketNumbers = (1..50).map {
        TicketNumber(number = it, ticketCategory = fhVorarlbergEvent8Ticketcategory2)
    }
    val fhVorarlbergEvent9Ticketcategory1TicketNumbers = (1..80).map {
        TicketNumber(number = it, ticketCategory = fhVorarlbergEvent9Ticketcategory1)
    }
    val fhVorarlbergEvent9Ticketcategory2TicketNumbers = (1..60).map {
        TicketNumber(number = it, ticketCategory = fhVorarlbergEvent9Ticketcategory2)
    }
    val kulturhausDornbirnEvent6Ticketcategory1TicketNumbers = (1..100).map {
        TicketNumber(number = it, ticketCategory = kulturhausDornbirnEvent6Ticketcategory1)
    }
    val kulturhausDornbirnEvent6Ticketcategory2TicketNumbers = (1..150).map {
        TicketNumber(number = it, ticketCategory = kulturhausDornbirnEvent6Ticketcategory2)
    }
    val kulturhausDornbirnEvent7Ticketcategory1TicketNumbers = (1..70).map {
        TicketNumber(number = it, ticketCategory = kulturhausDornbirnEvent7Ticketcategory1)
    }
    val kulturhausDornbirnEvent7Ticketcategory2TicketNumbers = (1..120).map {
        TicketNumber(number = it, ticketCategory = kulturhausDornbirnEvent7Ticketcategory2)
    }
    val  theaterAmSaumarktEvent5Ticketcategory1TicketNumbers = (1..150).map {
        TicketNumber(number = it, ticketCategory =  theaterAmSaumarktEvent5Ticketcategory1)
    }
    val  theaterAmSaumarktEvent5Ticketcategory2TicketNumbers = (1..90).map {
        TicketNumber(number = it, ticketCategory =  theaterAmSaumarktEvent5Ticketcategory2)
    }
    val  theaterAmSaumarktEvent6Ticketcategory1TicketNumbers = (1..200).map {
        TicketNumber(number = it, ticketCategory =  theaterAmSaumarktEvent6Ticketcategory1)
    }
    val  theaterAmSaumarktEvent6Ticketcategory2TicketNumbers = (1..150).map {
        TicketNumber(number = it, ticketCategory =  theaterAmSaumarktEvent6Ticketcategory2)
    }
    val  fhVorarlbergGruppeHTicketcategory1TicketNumbers = (1..200).map {
        TicketNumber(number = it, ticketCategory =  fhVorarlbergGruppeHTicketcategory1)
    }
    val  fhVorarlbergGruppeH2Ticketcategory1TicketNumbers = (1..200).map {
        TicketNumber(number = it, ticketCategory =  fhVorarlbergGruppeH2Ticketcategory1)
    }
    val  fhVorarlbergGruppeH3Ticketcategory1TicketNumbers = (1..200).map {
        TicketNumber(number = it, ticketCategory =  fhVorarlbergGruppeH3Ticketcategory1)
    }

    val  festeAmGarnmarktSommerTicketcategory1TicketNumbers = (1..300).map {
        TicketNumber(number = it, ticketCategory =  festeAmGarnmarktSommerTicketcategory1)
    }
    val  festeAmGarnmarktSommerTicketcategory2TicketNumbers = (1..200).map {
        TicketNumber(number = it, ticketCategory =  festeAmGarnmarktSommerTicketcategory2)
    }
    val  festeAmGarnmarktSommerKonzertTicketcategory1TicketNumbers = (1..400).map {
        TicketNumber(number = it, ticketCategory =  festeAmGarnmarktSommerKonzertTicketcategory1)
    }
    val  festeAmGarnmarktWinterTicketcategory1TicketNumbers = (1..300).map {
        TicketNumber(number = it, ticketCategory =  festeAmGarnmarktWinterTicketcategory1)
    }
    val  festeAmGarnmarktWinterTicketcategory2TicketNumbers = (1..100).map {
        TicketNumber(number = it, ticketCategory =  festeAmGarnmarktWinterTicketcategory1)
    }

    val  festeAmGarnmarktFruehlingsKonzertTicketcategory1TicketNumbers = (1..400).map {
        TicketNumber(number = it, ticketCategory =  festeAmGarnmarktFruehlingsKonzertTicketcategory1)
    }


    val events = listOf(
        kulturhausLecture,
        szeneDay1,
        szeneDay2,
        festspieleBregenz,
        theaterKellerEvent,
        hofsteigsaalEvent,
        montforthausFeldkirchEvent,
        kammgarnHardEvent,
        kulturhausDornbirnEvent2,
        kulturhausDornbirnEvent3,
        kulturhausDornbirnEvent4,
        theaterAmSaumarktEvent2,
        theaterAmSaumarktEvent3,
        theaterAmSaumarktEvent4,
        fhVorarlbergEvent4,
        fhVorarlbergEvent5,
        fhVorarlbergEvent8,
        fhVorarlbergEvent9,
        kulturhausDornbirnEvent6,
        kulturhausDornbirnEvent7,
        theaterAmSaumarktEvent5,
        theaterAmSaumarktEvent6,
        fhVorarlbergGruppeH,
        fhVorarlbergGruppeH2,
        fhVorarlbergGruppeH3,
        festeAmGarnmarktSommer,
        festeAmGarnmarktSommerKonzert,
        festeAmGarnmarktWinter,
        festeAmGarnmarktFruehlingsKonzert,
    )

    val ticketNumbers = listOf(
        kulturhausCategory1TicketNumbers,
        kulturhausCategory2TicketNumbers,
        szeneDay1TicketCategory1TicketNumbers,
        szeneDay2TicketCategory1TicketNumbers,
        festspieleBregenzTicketcategory1TicketNumbers,
        festspieleBregenzTicketcategory2TicketNumbers,
        theaterKellerEventTicketcategory1TicketNumbers,
        theaterKellerEventTicketcategory2TicketNumbers,
        hofsteigsaalEventTicketcategory1TicketNumbers,
        montforthausFeldkirchEventTicketcategory1TicketNumbers,
        montforthausFeldkirchEventTicketcategory2TicketNumbers,
        kammgarnHardEventTicketcategory1TicketNumbers,
        kammgarnHardEventTicketcategory2TicketNumbers,
        kulturhausDornbirnEvent2Ticketcategory1TicketNumbers,
        kulturhausDornbirnEvent2Ticketcategory2TicketNumbers,
        kulturhausDornbirnEvent2Ticketcategory3TicketNumbers,
        kulturhausDornbirnEvent3Ticketcategory1TicketNumbers,
        kulturhausDornbirnEvent3Ticketcategory2TicketNumbers,
        kulturhausDornbirnEvent4Ticketcategory1TicketNumbers,
        kulturhausDornbirnEvent4Ticketcategory2TicketNumbers,
        theaterAmSaumarktEvent2Ticketcategory1TicketNumbers,
        theaterAmSaumarktEvent2Ticketcategory2TicketNumbers,
        theaterAmSaumarktEvent3Ticketcategory1TicketNumbers,
        theaterAmSaumarktEvent3Ticketcategory2TicketNumbers,
        theaterAmSaumarktEvent4Ticketcategory1TicketNumbers,
        theaterAmSaumarktEvent4Ticketcategory2TicketNumbers,
        fhVorarlbergEvent4Ticketcategory1TicketNumbers,
        fhVorarlbergEvent4Ticketcategory2TicketNumbers,
        fhVorarlbergEvent5Ticketcategory1TicketNumbers,
        fhVorarlbergEvent5Ticketcategory2TicketNumbers,
        fhVorarlbergEvent8Ticketcategory1TicketNumbers,
        fhVorarlbergEvent8Ticketcategory2TicketNumbers,
        fhVorarlbergEvent9Ticketcategory1TicketNumbers,
        fhVorarlbergEvent9Ticketcategory2TicketNumbers,
        kulturhausDornbirnEvent6Ticketcategory1TicketNumbers,
        kulturhausDornbirnEvent6Ticketcategory2TicketNumbers,
        kulturhausDornbirnEvent7Ticketcategory1TicketNumbers,
        kulturhausDornbirnEvent7Ticketcategory2TicketNumbers,
        theaterAmSaumarktEvent5Ticketcategory1TicketNumbers,
        theaterAmSaumarktEvent5Ticketcategory2TicketNumbers,
        theaterAmSaumarktEvent6Ticketcategory1TicketNumbers,
        theaterAmSaumarktEvent6Ticketcategory2TicketNumbers,
        fhVorarlbergGruppeHTicketcategory1TicketNumbers,
        fhVorarlbergGruppeH2Ticketcategory1TicketNumbers,
        fhVorarlbergGruppeH3Ticketcategory1TicketNumbers,
        festeAmGarnmarktSommerTicketcategory1TicketNumbers,
        festeAmGarnmarktSommerTicketcategory2TicketNumbers,
        festeAmGarnmarktSommerKonzertTicketcategory1TicketNumbers,
        festeAmGarnmarktWinterTicketcategory1TicketNumbers,
        festeAmGarnmarktWinterTicketcategory2TicketNumbers,
        festeAmGarnmarktFruehlingsKonzertTicketcategory1TicketNumbers,
    )

    val ticketCategories = listOf(
        kulturhausLectureTicketcategory1,
        kulturhausLectureTicketcategory2,
        szeneDay1TicketCategory1,
        szeneDay2TicketCategory1,
        festspieleBregenzTicketcategory1,
        festspieleBregenzTicketcategory2,
        theaterKellerEventTicketcategory1,
        theaterKellerEventTicketcategory2,
        hofsteigsaalEventTicketcategory1,
        montforthausFeldkirchEventTicketcategory1,
        montforthausFeldkirchEventTicketcategory2,
        kammgarnHardEventTicketcategory1,
        kammgarnHardEventTicketcategory2,
        kulturhausDornbirnEvent2Ticketcategory1,
        kulturhausDornbirnEvent2Ticketcategory2,
        kulturhausDornbirnEvent2Ticketcategory3,
        kulturhausDornbirnEvent3Ticketcategory1,
        kulturhausDornbirnEvent3Ticketcategory2,
        kulturhausDornbirnEvent4Ticketcategory1,
        kulturhausDornbirnEvent4Ticketcategory2,
        theaterAmSaumarktEvent2Ticketcategory1,
        theaterAmSaumarktEvent2Ticketcategory2,
        theaterAmSaumarktEvent3Ticketcategory1,
        theaterAmSaumarktEvent3Ticketcategory2,
        theaterAmSaumarktEvent4Ticketcategory1,
        theaterAmSaumarktEvent4Ticketcategory2,
        fhVorarlbergEvent4Ticketcategory1,
        fhVorarlbergEvent4Ticketcategory2,
        fhVorarlbergEvent5Ticketcategory1,
        fhVorarlbergEvent5Ticketcategory2,
        fhVorarlbergEvent8Ticketcategory1,
        fhVorarlbergEvent8Ticketcategory2,
        fhVorarlbergEvent9Ticketcategory1,
        fhVorarlbergEvent9Ticketcategory2,
        kulturhausDornbirnEvent6Ticketcategory1,
        kulturhausDornbirnEvent6Ticketcategory2,
        kulturhausDornbirnEvent7Ticketcategory1,
        kulturhausDornbirnEvent7Ticketcategory2,
        theaterAmSaumarktEvent5Ticketcategory1,
        theaterAmSaumarktEvent5Ticketcategory2,
        theaterAmSaumarktEvent6Ticketcategory1,
        theaterAmSaumarktEvent6Ticketcategory2,
        fhVorarlbergGruppeHTicketcategory1,
        fhVorarlbergGruppeH2Ticketcategory1,
        fhVorarlbergGruppeH3Ticketcategory1,
        festeAmGarnmarktSommerTicketcategory1,
        festeAmGarnmarktSommerTicketcategory2,
        festeAmGarnmarktSommerKonzertTicketcategory1,
        festeAmGarnmarktWinterTicketcategory1,
        festeAmGarnmarktWinterTicketcategory2,
        festeAmGarnmarktFruehlingsKonzertTicketcategory1,
    )

    init {
        val em = PersistenceManager.getEntityManagerInstance()

        if (removeOldData) {
            em.transaction.begin()
            listOf(
                "Account",
                "AccountPermission",
                "Event",
                "Location",
                "Order",
                "Organiser",
                "Payment",
                "Series",
                "Ticket",
                "TicketCategory",
                "TicketNumber",
                "WebUser",
            ).forEach {
                em.createQuery("DELETE FROM $it").executeUpdate()
            }

            em.transaction.commit()
        }

        em.transaction.begin()
        listOf(
            fhVorarlberg,
            jugendvereinLustenau,
            bregenzerFestspiele,
            kulturhausDornbirn,
            feldkircherFestspiele,
            hohenemsKultur,
            theaterAmSaumarkt,
            theaterKosmos,
            vorarlbergerLandestheater,
            bodenseearena,
            stadtGoetzis,

            kulturhausDornbirnLocation,
            szeneLustenauLocation,
            festspieleBregenzLocation,
            kulturhausDornbirnLocation,
            szeneLustenauLocation,
            festspieleBregenzLocation,
            theaterKellerLocation,
            hofsteigsaalLocation,
            montforthausFeldkirchLocation,
            kammgarnHardLocation,
            kulturhausDornbirnLocation2 ,
            kulturhausDornbirnLocation3 ,
            kulturhausDornbirnLocation4,
            theaterAmSaumarktLocation2 ,
            theaterAmSaumarktLocation3,
            theaterAmSaumarktLocation4,
            fhVorarlbergLocation4 ,
            fhVorarlbergLocation5,
            fhVorarlbergLocation6,
            fhVorarlbergLocation7,
            fhVorarlbergLocation8,
            fhVorarlbergLocation9,
            kulturhausDornbirnLocation5,
            kulturhausDornbirnLocation6 ,
            theaterAmSaumarktLocation5,
            theaterAmSaumarktLocation6,
            festeAmGarnmarktLocation,

            lectures,
            szeneOpenAir,
            festspiele,
            operettenFestspiele,
            hofsteigKultur,
            kosmosKonzerte,
            vorarlbergJazz,
            bodenseeClassic,
            kulturhausDornbirnSeries2,
            kulturhausDornbirnSeries3,
            theaterAmSaumarktSeries2,
            theaterAmSaumarktSeries3,
            szeneOpenAir2023,
            festspiele2023,
            operettenFestspiele2023,
            hofsteigKultur2023,
            kosmosKonzerte2023,
            vorarlbergJazz2023,
            bodenseeClassic2023,
            lecturesGruppeH,
            gruppeHPushen,
            gruppeHDaily,
            festeAmGarnmarktSeries,
            worldTourSeries,
            wordlTourRock,

            events,

            ticketCategories,

            ticketNumbers,

            defaultAccount,
        ).forEach {
            em.persist(it)
        }

        em.transaction.commit()

        em.clear()
        em.close()
    }
}