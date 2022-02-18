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


public class TourPDFCreator
{
    public static InputStreamSource createPDFTicket(TourDTO tourDTO, TourPurchaseDTO tourPurchaseDTO) throws IOException
    {
        try(PDDocument document = new PDDocument())
        {
            PDPage page = new PDPage();
            document.addPage(page);

            try(PDPageContentStream contentStream = new PDPageContentStream(document, page))
            {
                contentStream.setFont(PDType1Font.COURIER, 12);
                contentStream.beginText();
                contentStream.showText("hello mate");
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