/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.util.implement;

import id.my.berkah.util.config.MyBatisConnectionFactory;
import id.my.berkah.util.model.AuthBURestoreModel;
import id.my.berkah.util.model.DownloadRecoveryModel;
import id.my.berkah.util.model.Qry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

/**
 *
 */
public class UtilImpl
{

    private final SqlSessionFactory sqlSessionFactory;

    public UtilImpl()
    {
        sqlSessionFactory = MyBatisConnectionFactory.getSqlSessionFactory();
    }

    public Map GetSQLQuery(Map map)
    {
        SqlSession session = sqlSessionFactory.openSession();
        try
        {
            session.selectOne("POSDownload.GetSQLQuery", map);
            return map;
        } finally
        {
            session.close();
        }
    }

    public Map UpdateResult(Map map)
    {
        SqlSession session = sqlSessionFactory.openSession();
        try
        {
            session.selectOne("id.my.berkah.util.UtilMapper.UpdateResult", map);
            return map;
        } finally
        {
            session.close();
        }
    }

    public List<Qry> getListTableDownload(Map map)
    {
        SqlSession session = sqlSessionFactory.openSession();
        List<Qry> out = new ArrayList<>();
        try
        {
            out = session.selectList("POSDownload.getListTableDownload", map);
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            session.close();
        }
        return out;
    }

    public String downloadMainDaily(Map map)
    {
        String status = "0";
        SqlSession session = sqlSessionFactory.openSession();
        try
        {
            session.selectOne("POSDownload.downloadMainDaily", map);
        } catch (Exception e)
        {
            status = "1";
            e.printStackTrace();
        } finally
        {
            session.close();
        }
        return status;
    }

    public Object downloadRecovery(DownloadRecoveryModel param)
    {
        SqlSession session = sqlSessionFactory.openSession();
        DownloadRecoveryModel out = new DownloadRecoveryModel();
        try
        {
            session.selectOne("POSDownload.downloadRecovery", param);
            out.setOutTempDMLGenerateId(param.getOutFilename());
            out.setOutFilename(param.getOutFilename());
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            session.close();
        }
        return out;
    }
    

    public Object getAuthBURestore(AuthBURestoreModel param)
    {
        SqlSession session=sqlSessionFactory.openSession();
        AuthBURestoreModel model=new AuthBURestoreModel();
        try {
            session.selectOne("AuthBURestoreMapper.getAuthBURestore",param);
            model.setOutError(param.getOutError());
            model.setOutMessage(param.getOutMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally{
           session.close();
        }
        return model;
    }
}
