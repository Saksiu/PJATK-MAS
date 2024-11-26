package big.game;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.awt.*;

@Converter(autoApply = true)
public class ColorConverter implements AttributeConverter<Color, String>
{
    @Override
    public String convertToDatabaseColumn(Color attribute)
    {
        String hex = "#" + Integer.toHexString(attribute.getRGB()).substring(0, 6);
        return hex;
    }

    @Override
    public Color convertToEntityAttribute(String dbData)
    {
        Color color = Color.decode(dbData);
        return color;
    }
}
