package museum.service.utilities;

import museum.service.models.DTOs.TourDTO;
import museum.service.models.DTOs.TourPurchaseDTO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;


public class TourPDFCreator
{
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    public static InputStreamSource createPDFTicket(TourDTO tourDTO, TourPurchaseDTO tourPurchaseDTO) throws IOException
    {
        try(PDDocument document = new PDDocument())
        {
            PDPage page = new PDPage();
            document.addPage(page);

            try(PDPageContentStream contentStream = new PDPageContentStream(document, page))
            {
                contentStream.beginText();
                contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
                contentStream.setLeading(14.5f);

                contentStream.newLineAtOffset(25, 700);

                contentStream.showText("Sifra karte: " + tourPurchaseDTO.getPurchaseID().toString());
                contentStream.newLine();

                contentStream.showText("Datum i vrijeme pocetka obilaska: " + tourDTO.getStartTimestamp().format(dateTimeFormatter));
                contentStream.newLine();

                contentStream.showText("Datum i vrijeme zavrsetka obilaska: " + tourDTO.getEndTimestamp().format(dateTimeFormatter));
                contentStream.newLine();

                contentStream.showText("Datum i vrijeme podnosenja zahtjeva za kupovinom karte: " + tourDTO.getPurchased().format(dateTimeFormatter));
                contentStream.newLine();
                contentStream.showText("Datum i vrijeme primitka uplate: " + tourDTO.getPaid().format(dateTimeFormatter));
                contentStream.newLine();

                contentStream.showText("Cijena: " + tourDTO.getPrice().toString());
                contentStream.newLine();

                contentStream.endText();
                contentStream.close();

                ByteArrayOutputStream fileStream = new ByteArrayOutputStream();
                document.save(fileStream);

                InputStreamSource stream = new ByteArrayResource(fileStream.toByteArray(), "Karta.pdf");
                fileStream.close();

                return stream;
            }
        }
    }
}
