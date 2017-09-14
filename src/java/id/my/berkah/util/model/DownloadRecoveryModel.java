/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.util.model;

/**
 *
 * @author Ari
 */
public class DownloadRecoveryModel
{

    String InUserId,
            InParamBu,
            OutTempDMLGenerateId,
            OutFilename;

    public String getInUserId()
    {
        return InUserId;
    }

    public void setInUserId(String InUserId)
    {
        this.InUserId = InUserId;
    }

    public String getInParamBu()
    {
        return InParamBu;
    }

    public void setInParamBu(String InParamBu)
    {
        this.InParamBu = InParamBu;
    }

    public String getOutTempDMLGenerateId()
    {
        return OutTempDMLGenerateId;
    }

    public void setOutTempDMLGenerateId(String OutTempDMLGenerateId)
    {
        this.OutTempDMLGenerateId = OutTempDMLGenerateId;
    }

    public String getOutFilename()
    {
        return OutFilename;
    }

    public void setOutFilename(String OutFilename)
    {
        this.OutFilename = OutFilename;
    }
    
    
}
