package dk.bierproductie.opc_ua_client.core;

import org.eclipse.milo.opcua.sdk.core.nodes.Node;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;

import java.util.HashMap;
import java.util.Map;

public class NodeFactory {

    public Map<String, NodeId> adminNodeMap;
    public Map<String, NodeId> statusNodeMap;
    public Map<String, NodeId> commandNodeMap;

    public NodeFactory() {
        adminNodeMap = new HashMap<>();
        adminNodeMap.put("ProducedProducts", new NodeId(6,"::Program:Cube.Admin.ProdProcessedCount"));
        adminNodeMap.put("DefectiveProducts", new NodeId(6,"::Program:Cube.Admin.ProdDefectiveCount"));
        adminNodeMap.put("StopReasonId", new NodeId(6,"::Program:Cube.Admin.StopReason.ID"));
        adminNodeMap.put("StopReasonValue", new NodeId(6,"::Program:Cube.Admin.StopReason.Value"));
        adminNodeMap.put("IDofProductInBatch", new NodeId(6,"::Program:Cube.Admin.Parameter[0].Value"));

        statusNodeMap = new HashMap<>();
        statusNodeMap.put("MachineState", new NodeId(6,"::Program:Cube.Status.StateCurrent"));
        statusNodeMap.put("MachineSpeed", new NodeId(6,"::Program:Cube.Status.MachSpeed"));
        statusNodeMap.put("NormalizedMachineSpeed", new NodeId(6,"::Program:Cube.Status.CurMachSpeed"));
        statusNodeMap.put("CurrentBatchID", new NodeId(6,"::Program:Cube.Status.Parameter[0].Value"));
        statusNodeMap.put("ProductsInCurrentBatch", new NodeId(6,"::Program:Cube.Status.Parameter[1].Value"));
        statusNodeMap.put("Humidity", new NodeId(6,"::Program:Cube.Status.Parameter[2].Value"));
        statusNodeMap.put("Temperature", new NodeId(6,"::Program:Cube.Status.Parameter[3].Value"));
        statusNodeMap.put("Vibration", new NodeId(6,"::Program:Cube.Status.Parameter[4].Value"));

        commandNodeMap = new HashMap<>();
        commandNodeMap.put("SetMachineSpeed", new NodeId(6,"::Program:Cube.Command.MachSpeed"));
        commandNodeMap.put("SetMachineCommand", new NodeId(6,"::Program:Cube.Command.CntrlCmd"));
        commandNodeMap.put("ExecuteMachineCommand", new NodeId(6,"::Program:Cube.Command.CmdChangeRequest"));
        commandNodeMap.put("NextBatchID", new NodeId(6,"::Program:Cube.Command.Parameter[0]"));
        commandNodeMap.put("ProductIdForNextBatch", new NodeId(6,"::Program:Cube.Command.Parameter[1]"));
        commandNodeMap.put("ProductsInNextBatch", new NodeId(6,"::Program:Cube.Command.Parameter[2]"));
    }
}
