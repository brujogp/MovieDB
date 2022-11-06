//
// Created by Bruno Joctán García Palma on 06/11/22.
// Copyright (c) 2022 soyjoctan. All rights reserved.
//

import Foundation
import SwiftUI
import shared

struct ViewListMovies: View {
    var genreItem: Genre

    var body: some View {
        Text(genreItem.name!)
                .navigationBarTitle("Género: " + genreItem.name!)
    }
}