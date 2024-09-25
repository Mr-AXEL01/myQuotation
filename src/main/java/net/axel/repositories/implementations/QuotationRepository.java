package net.axel.repositories.implementations;

import net.axel.config.DatabaseConnection;
import net.axel.models.entities.Quotation;
import net.axel.repositories.interfaces.IQuotationRepository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class QuotationRepository implements IQuotationRepository {

    private final String tableName = "quotations";
    private final Connection connection = DatabaseConnection.getInstance().getConnection();

    public QuotationRepository() throws SQLException {
    }

    @Override
    public Quotation addQuotation(Quotation quotation) {
        final String query = "INSERT INTO "+ tableName +" (id, estimated_amount, issued_date, validity_date, accepted, project_id)" +
                " VALUES(?, ?, ?, ?, ?, ?)";
        try(PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, quotation.getQuotationId());
            stmt.setDouble(2, quotation.getEstimatedAmount());
            stmt.setDate(3, Date.valueOf(quotation.getIssuedDate()));
            stmt.setDate(4, Date.valueOf(quotation.getValidityDate()));
            stmt.setBoolean(5, quotation.getAccepted());
            stmt.setObject(6, quotation.getProject().getId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Failed to add quotation, no rows affected.");
            }
        } catch(SQLException e) {
            throw new RuntimeException("Error adding Quotation : " + e.getMessage());
        }
        return quotation;
    }


}
