/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mihir;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

@ActionID(
        category = "File",
        id = "org.mihir.StackListener"
)
@ActionRegistration(
        iconBase = "org/mihir/icon.PNG",
        displayName = "#CTL_StackListener"
)
@ActionReference(path = "Toolbars/File", position = 0)
@Messages("CTL_StackListener=StackView")
public final class StackListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO implement action body
    }
}
